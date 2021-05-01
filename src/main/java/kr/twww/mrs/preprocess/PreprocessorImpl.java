package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.DataReader;
import kr.twww.mrs.data.DataReaderImpl;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.User;
import kr.twww.mrs.preprocess.object.Score;
import kr.twww.mrs.preprocess.predict.Predictor;
import kr.twww.mrs.preprocess.predict.PredictorImpl;
import org.apache.spark.mllib.recommendation.Rating;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PreprocessorImpl extends PreprocessorBase implements Preprocessor
{
    private final DataReader dataReader;

    private final int N = (1 << 19);

    public PreprocessorImpl()
    {
        dataReader = new DataReaderImpl();
    }

    @Override
    public ArrayList<Score> GetRecommendList( String _gender, String _age, String _occupation )
    {
        return GetRecommendList(_gender, _age, _occupation, "");
    }

    @Override
    public ArrayList<Score> GetRecommendList( String _gender, String _age, String _occupation, String _categories )
    {
        var gender = User.ConvertGender(_gender);
        var age = User.ConvertAge(_age);
        var occupation = User.ConvertOccupationByText(_occupation);
        var categoryList = GetCategoryList(_categories);

        return GetScoreList(
                gender,
                age,
                occupation,
                categoryList
        );
    }

    @Override
    public ArrayList<Movie.Genre> GetCategoryList( String genreText )
    {
        if ( genreText == null ) return null;
        if ( genreText.isEmpty() ) return new ArrayList<>();

        var result = new ArrayList<Movie.Genre>();

        var splitText = genreText.split("\\|");

        for ( var i : splitText )
        {
            var genre = Movie.ConvertGenre(i);

            if ( genre == null ) return null;

            result.add(genre);
        }

        return result;
    }

    @Override
    public ArrayList<Score> GetScoreList(
            User.Gender gender,
            User.Age age,
            User.Occupation occupation,
            ArrayList<Movie.Genre> genreList
    )
    {
        if ( gender == null ) return null;
        if ( age == null ) return null;
        if ( occupation == null ) return null;
        if ( genreList == null ) return null;

        var userList = dataReader.GetUserList();
        var movieList = dataReader.GetMovieList();
        var ratingList = dataReader.GetRatingList();

        if ( userList == null ) return null;
        if ( movieList == null ) return null;
        if ( ratingList == null ) return null;

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
                movieList
        );

        // 3. 유저 최대 N명 선택
        filteredUserList = SelectFilteredUser(ratingList, filteredUserList, filteredMovieList);

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
        scoreList.sort((o1, o2) -> Double.compare(o2.score, o1.score));

        var count = scoreList.size();

        if ( count > 10 )
        {
            count = 10;
        }

        return new ArrayList<>(scoreList.subList(0, count));
    }

    private List<User> SelectFilteredUser(
            ArrayList<Rating> ratingList,
            List<User> filteredUserList,
            List<Movie> filteredMovieList
    )
    {
        if ( filteredUserList.isEmpty() ) return filteredUserList;
        if ( filteredMovieList.isEmpty() ) return null;

        var size = filteredUserList.size();
        var maxUserCount = N / filteredMovieList.size();

        if ( size <= maxUserCount ) return filteredUserList;

        var max = filteredUserList.get(filteredUserList.size() - 1).userId;

        var ratingCount = new int[max];
        Arrays.fill(ratingCount, 0);

        ratingList.forEach(rating -> {
            var userId = rating.user();

            if ( userId <= max )
            {
                ++ratingCount[userId - 1];
            }
        });

        filteredUserList.sort(
                Comparator.comparingInt(o -> ratingCount[o.userId - 1])
        );

        var selectUserList = new ArrayList<User>();

        var count = 0.0;
        var step = (double)size / maxUserCount;

        while ( count < (size - 1) )
        {
            var index = (int)count;
            count += step;

            selectUserList.add(filteredUserList.get(index));
        }

        return selectUserList;
    }

    private List<User> GetFilteredUserList(
            User.Gender gender,
            User.Age age,
            User.Occupation occupation,
            ArrayList<User> userList
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

    private List<User> GetFilteredUserStream(
            User.Gender gender,
            User.Age age,
            User.Occupation occupation,
            ArrayList<User> userList,
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
                    unknownCount - 1);
        }

        return result;
    }

    private List<Movie> GetFilteredMovieList(
            ArrayList<Movie.Genre> genreList,
            List<Movie> movieList
    )
    {
        if ( genreList.isEmpty() )
        {
            return movieList;
        }

        return movieList
                .stream()
                .filter(movie -> genreList
                        .stream()
                        .anyMatch(genre -> movie.genres.contains(genre))
                ).collect(Collectors.toList());
    }

    private List<Rating> GetPredictList(
            List<User> filteredUserList,
            List<Movie> filteredMovieList,
            ArrayList<Rating> ratingList
    )
    {
        Predictor predictor = new PredictorImpl(dataReader);

        if ( !predictor.LoadModel() )
        {
            if ( !predictor.CreateModel(ratingList) )
            {
                System.out.println("Error: Create model failed");

                predictor.Close();
                return null;
            }
        }

        var predictList =
                predictor.GetPredictList(
                        filteredUserList,
                        filteredMovieList
                );

        predictor.Close();

        return predictList;
    }

    private ArrayList<Score> ToScoreList(
            List<Movie> filteredMovieList,
            List<Rating> predictList
    )
    {
        var scoreList = new ArrayList<Score>();

        var linkList = dataReader.GetLinkList();

        if ( linkList == null ) return null;

        predictList.forEach(_rating -> {
            var movieId = _rating.product();
            var rating = _rating.rating();

            var findScore = scoreList
                    .stream()
                    .filter(score -> score.movie.movieId == movieId)
                    .findFirst();

            if ( findScore.isPresent() )
            {
                findScore.get().score += rating;
            }
            else
            {
                var score = new Score();

                score.movie = filteredMovieList
                        .stream()
                        .filter(_movie -> _movie.movieId == movieId)
                        .findFirst()
                        .get();

                score.link = linkList
                        .stream()
                        .filter(_link -> _link.movieId == movieId)
                        .findFirst()
                        .get();

                score.score = rating;

                scoreList.add(score);
            }
        });

        return scoreList;
    }
}

