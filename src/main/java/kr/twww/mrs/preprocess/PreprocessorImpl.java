package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.DataReader;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.User;
import kr.twww.mrs.preprocess.object.Score;
import kr.twww.mrs.preprocess.predict.Predictor;
import org.apache.spark.mllib.recommendation.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("FieldCanBeLocal")
@Service
public class PreprocessorImpl extends PreprocessorBase implements Preprocessor
{
    @Autowired
    private DataReader dataReader;

    @Autowired
    private Predictor predictor;

    private final int MAX_PAIR_COUNT = 624000;
    private final int MIN_RATING_COUNT = 10;

    @Override
    public ArrayList<Score> GetRecommendList( String _gender, String _age, String _occupation ) throws Exception
    {
        return GetRecommendList(_gender, _age, _occupation, "");
    }

    @Override
    public ArrayList<Score> GetRecommendList( String _gender, String _age, String _occupation, String _categories ) throws Exception
    {
        var gender = User.ConvertGender(_gender);
        var age = User.ConvertAge(_age);
        var occupation = User.ConvertOccupationByText(_occupation);
        var categoryList = GetCategoryList(_categories);

        return GetScoreListByUser(
                gender,
                age,
                occupation,
                categoryList
        );
    }

    @Override
    public ArrayList<Score> GetRecommendList( String _title, String _limit ) throws Exception
    {
        var title = GetMovieFromTitle(_title);
        var limit = ConvertLimit(_limit);

        return null;
    }

    @Override
    public ArrayList<Movie.Genre> GetCategoryList( String genreText ) throws Exception
    {
        if ( genreText == null ) return null;
        if ( genreText.isEmpty() ) return new ArrayList<>();

        var result = new ArrayList<Movie.Genre>();

        var splitText = genreText.split("\\|");

        for ( var i : splitText )
        {
            result.add(Movie.ConvertGenre(i));
        }

        return result;
    }

    @Override
    public Movie GetMovieFromTitle( String _title ) throws Exception
    {
        if ( _title == null )
        {
            throw new Exception("Invalid title string");
        }

        var result = dataReader.GetMovieList().stream().filter(movie -> {
            var a = movie.title
                    .toLowerCase()
                    .replaceAll("\\s+", "");

            var b = _title
                    .toLowerCase()
                    .replaceAll("\\s+", "");

            return a.equals(b);
        }).findFirst().orElse(null);

        if ( result == null )
        {
            throw new Exception("Cannot find movie title");
        }

        return result;
    }

    @Override
    public int ConvertLimit( String _limit ) throws Exception
    {
        if ( _limit == null )
        {
            throw new Exception("Invalid limit string");
        }

        var result = Integer.parseInt(_limit);

        if ( !Integer.toString(result).equals(_limit) )
        {
            throw new Exception("Invalid limit string");
        }

        return result;
    }

    @Override
    public ArrayList<Score> GetScoreListByUser(
            User.Gender gender,
            User.Age age,
            User.Occupation occupation,
            ArrayList<Movie.Genre> genreList
    ) throws Exception
    {
        if ( gender == null ) return null;
        if ( age == null ) return null;
        if ( occupation == null ) return null;
        if ( genreList == null ) return null;

        System.out.println("Info: Loading data ... ");

        var userList = dataReader.GetUserList();
        var movieList = dataReader.GetMovieList();
        var ratingList = dataReader.GetRatingList();

        if ( userList == null ) return null;
        if ( movieList == null ) return null;
        if ( ratingList == null ) return null;

        System.out.println("Info: Preprocessing ... ");

        // 1. 유저 필터
        var filteredUserList = GetFilteredUserList(
                gender,
                age,
                occupation,
                userList
        );

        // 2. 영화 필터
        var filteredMovieList = GetFilteredMovieList(
                genreList,
                movieList,
                ratingList
        );

        // 3. 유저 최대 N명 선택
        filteredUserList = SelectFilteredUser(
                ratingList,
                filteredUserList,
                filteredMovieList
        );

        if ( filteredUserList == null ) return null;

        // 4. ALS 예측
        var predictList = GetPredictList(
                filteredUserList,
                filteredMovieList,
                ratingList
        );

        if ( predictList == null ) return null;

        // 5. Score 리스트 작성
        var scoreList = ToScoreList(
                filteredMovieList,
                predictList
        );

        if ( scoreList == null ) return null;

        // 6. 내림차순 정렬 및 상위 10개
        return SortingTopList(scoreList);
    }

    @Override
    public ArrayList<Score> GetScoreListByMovie( Movie movie, int limit )
    {
        return null;
    }

    private List<User> GetFilteredUserList(
            User.Gender gender,
            User.Age age,
            User.Occupation occupation,
            List<User> userList
    )
    {
        var unknownCount = 3;

        if ( gender == User.Gender.UNKNOWN ) --unknownCount;
        if ( age == User.Age.UNKNOWN ) --unknownCount;
        if ( occupation == User.Occupation.UNKNOWN ) --unknownCount;

        if ( unknownCount == 0 ) return userList;

        return GetFilteredUserStream(
                gender,
                age,
                occupation,
                userList,
                unknownCount
        );
    }

    private List<User> SelectFilteredUser(
            List<Rating> ratingList,
            List<User> filteredUserList,
            List<Movie> filteredMovieList
    )
    {
        if ( filteredUserList.isEmpty() ) return filteredUserList;
        if ( filteredMovieList.isEmpty() ) return null;

        var size = filteredUserList.size();
        var maxUserCount = MAX_PAIR_COUNT / filteredMovieList.size();

        if ( size <= maxUserCount ) return filteredUserList;

        var max = filteredUserList.get(filteredUserList.size() - 1).userId;

        var ratingCount = new int[max + 1];
        Arrays.fill(ratingCount, 0);

        ratingList.forEach(rating -> {
            var userId = rating.user();

            if ( userId > max ) return;

            ++ratingCount[userId];
        });

        filteredUserList.sort(
                Comparator.comparingInt(o -> ratingCount[o.userId])
        );

        return filteredUserList.subList(
                filteredUserList.size() - maxUserCount,
                filteredUserList.size()
        );
    }

    private List<Movie> GetFilteredMovieList(
            List<Movie.Genre> genreList,
            List<Movie> movieList,
            List<Rating> ratingList
    )
    {
        if ( movieList.isEmpty() ) return movieList;

        var max = movieList.get(movieList.size() - 1).movieId;

        var ratingCount = new int[max + 1];
        Arrays.fill(ratingCount, 0);

        ratingList.forEach(rating -> {
            var movieId = rating.product();

            if ( movieId > max ) return;

            ++ratingCount[movieId];
        });

        return movieList
                .stream()
                .filter(movie -> {
                    if ( ratingCount[movie.movieId] < MIN_RATING_COUNT )
                    {
                        return false;
                    }

                    if ( genreList.isEmpty() ) return true;

                    return genreList
                            .stream()
                            .anyMatch(genre -> movie.genres.contains(genre));
                }).collect(Collectors.toList());
    }

    private List<User> GetFilteredUserStream(
            User.Gender gender,
            User.Age age,
            User.Occupation occupation,
            List<User> userList,
            int unknownCount
    )
    {
        var result = userList
                .stream()
                .filter(user -> {
                    var count = 0;

                    if ( gender != User.Gender.UNKNOWN )
                    {
                        if ( user.gender == gender )
                        {
                            ++count;
                        }
                    }

                    if ( age != User.Age.UNKNOWN )
                    {
                        if ( user.age == age )
                        {
                            ++count;
                        }
                    }

                    if ( occupation != User.Occupation.UNKNOWN )
                    {
                        if ( user.occupation == occupation )
                        {
                            ++count;
                        }
                    }

                    return (count >= unknownCount);
                }).collect(Collectors.toList());

        if ( result.isEmpty() && (unknownCount > 1) )
        {
            result = GetFilteredUserStream(
                    gender,
                    age,
                    occupation,
                    userList,
                    unknownCount - 1
            );
        }

        return result;
    }

    private List<Rating> GetPredictList(
            List<User> filteredUserList,
            List<Movie> filteredMovieList,
            ArrayList<Rating> ratingList
    ) throws Exception
    {
        if ( !predictor.LoadModel() )
        {
            if ( !predictor.CreateModel(ratingList) )
            {
                throw new Exception("Create model failed");
            }
        }

        return predictor.GetPredictList(
                filteredUserList,
                filteredMovieList
        );
    }

    private ArrayList<Score> ToScoreList(
            List<Movie> filteredMovieList,
            List<Rating> predictList
    ) throws Exception
    {
        if ( filteredMovieList.isEmpty() ) return null;

        var linkList = dataReader.GetLinkList();

        if ( linkList == null ) return null;

        var max = filteredMovieList.get(filteredMovieList.size() - 1).movieId;

        var scoreFullList = new ArrayList<Score>();
        var scoreList = new ArrayList<Score>();

        for ( var i = 0; i < (max + 1); ++i )
        {
            scoreFullList.add(new Score());
        }

        filteredMovieList.forEach(
                movie -> scoreFullList.get(movie.movieId).movie = movie
        );

        predictList.forEach(
                rating -> scoreFullList.get(rating.product()).score += rating.rating()
        );

        linkList.forEach(link -> {
            var movieId = link.movieId;

            if ( movieId > max ) return;

            scoreFullList.get(movieId).link = link;

            scoreList.add(scoreFullList.get(movieId));
        });

        return scoreList;
    }

    private ArrayList<Score> SortingTopList( ArrayList<Score> scoreList )
    {
        scoreList.sort(
                (o1, o2) -> Double.compare(o2.score, o1.score)
        );

        var count = scoreList.size();

        if ( count > 10 )
        {
            count = 10;
        }

        return new ArrayList<>(scoreList.subList(0, count));
    }
}

