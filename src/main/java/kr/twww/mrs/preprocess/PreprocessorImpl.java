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

public class PreprocessorImpl extends PreprocessorBase implements Preprocessor
{
    private final DataReader dataReader;

    private final int MAX_PAIR_COUNT = 624000;
    private final int MIN_RATING_COUNT = 10;

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
    public ArrayList<Score> GetRecommendList( String _title, String _limit)
    {
        var Limit = -1;
        var Title = _title;
        if(Title == null) return null;
        try{
            Limit = Integer.parseInt(_limit);
        }catch (NumberFormatException e) {
            return null;
        }
        if(Limit == -1) return null;

        return GetScoreList_2(
                Title,
                Limit
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
        scoreList.sort((o1, o2) -> Double.compare(o2.score, o1.score));

        var count = scoreList.size();

        if ( count > 10 )
        {
            count = 10;
        }

        return new ArrayList<>(scoreList.subList(0, count));
    }

    // 이함수에서 만들어야하는것: 영화에 평점을 높게준 최소카운트를 넘기는 유저 160명 고르기
    // 원래있던 GetScoreList를 변형해서 그 유저 160명이 주는 영화 예상 평점 받아오기
    // 함수 마지막부분을 바꿔서 이 함수를 통해 장르 필터링과 전체목록을 둘다 받을수있게 만들기.
    @Override
    public ArrayList<Score> GetScoreList_2(
            String Title,
            int limit
    )
    {
        System.out.println("Info: Loading data ... ");

        var userList = dataReader.GetUserList();
        var movieList = dataReader.GetMovieList();
        var ratingList = dataReader.GetRatingList();

        if ( userList == null ) return null;
        if ( movieList == null ) return null;
        if ( ratingList == null ) return null;

        System.out.println("Info: Preprocessing ... ");

        // 1.영화 찾기
        var findMovie = GetFindMovie(
                Title,
                movieList
        );

        // 2.이 영화에 평점 높게준 상위 160명 유저 찾기
        var findUserList = GetFindUser(
                findMovie,
                userList,
                ratingList
        );

        // 3.찾은 유저 160명의 전체 영화 예상평점 받아옴
        var predictList_2 = GetPredictList(
                findUserList,
                movieList,
                ratingList
        );

        // 4. Score 리스트 만들기

        // 5-1. Score 리스트중에서 장르 필터링해서 [limit/2]을 올림한 숫자 만큼 상위

        // 5-2 Score 리스트중에서 장르 해당안된 영화중 (limit - (5-1)) 만큼 상위

        // 6. 5-1 5-2 번갈아 출력

        return new ArrayList<>();

    }

    private List<Movie> GetFindMovie(
            String Title,
            List<Movie> movieList
    )
    {
        var findmovie= movieList
                .stream()
                .filter(movie -> movie.title.equals(Title) )
                .collect((Collectors.toList()));
        return findmovie;
    }

    private List<User> GetFindUser(
            List<Movie> findMovie,
            List<User> userList,
            List<Rating> ratingList
    )
    {
        return new ArrayList<>();
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
                    unknownCount - 1);
        }

        return result;
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
}

