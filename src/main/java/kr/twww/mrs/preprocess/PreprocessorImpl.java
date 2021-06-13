package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.DataReader;
import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.User;
import kr.twww.mrs.preprocess.object.Score;
import kr.twww.mrs.preprocess.predict.Predictor;
import org.apache.spark.mllib.recommendation.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings({"FieldCanBeLocal", "SpringJavaAutowiredFieldsWarningInspection"})
@Service
public class PreprocessorImpl extends PreprocessorBase implements Preprocessor
{
    @Autowired
    public DataReader dataReader;

    @Autowired
    public Predictor predictor;

    //@Autowired
    //public static ArrayList<Score> indexhtmlScoreList;

    //index.html 에서 사용하는 전체 scorelist.
    public ArrayList<Score> indexhtmlScoreList;

    private final int MAX_PAIR_COUNT = 624000;
    private final int MIN_RATING_COUNT = 10;

    /*
    main 실행 되고나서 초기화 루틴.
    GetScoreListByUserAll 로 전체 scorelist 를 구하면서,
    model 생성/학습, user/movie 등 전체 db에 업로드.
     */
    @PostConstruct
    public void PreprocessorImplstart() throws Exception {
        var gender = User.ConvertGender("");
        var occupation = User.ConvertOccupationByText("");
        var age = User.ConvertAge("");
        var category = GetCategoryList("");
        indexhtmlScoreList = GetScoreListByUserAll(gender,age,occupation,category);
        System.out.println("\n" +
                "\n" +
                "=============================================================\n" +
                "   ______                                   _    _    \n" +
                " .' ___  |                                 | |  | |   \n" +
                "/ .'   \\_| _ .--.   .--.   __   _  _ .--.  | |__| |_  \n" +
                "| |   ____[ `/'`\\]/ .'`\\ \\[  | | |[ '/'`\\ \\|____   _| \n" +
                "\\ `.___]  || |    | \\__. | | \\_/ |,| \\__/ |    _| |_  \n" +
                " `._____.'[___]    '.__.'  '.__.'1`_/| ;.__/    |_____| \n" +
                "                                  [__|                \n" +
                "=============================================================\n" +
                " ******* Initialization complete. Ready to run. *******\n" +
                " MongoDB log is on, to show that this program is using MongoDB.\n"+
                " Disable MongoDB log manually if you wish.\n" +
                "=============================================================\n" +
                "\n");
    }

    @Override
    public ArrayList<Score> getindexhtmlScoreList() throws Exception {
        if(indexhtmlScoreList.isEmpty()){
            var gender = User.ConvertGender("");
            var occupation = User.ConvertOccupationByText("");
            var age = User.ConvertAge("");
            var category = GetCategoryList("");
            indexhtmlScoreList = GetScoreListByUserAll(gender,age,occupation,category);
        }
        var tmpScoreList = indexhtmlScoreList;
        return tmpScoreList;
    }

    @Override
    public ArrayList<Score> GetRecommendList(
            String _gender,
            String _age,
            String _occupation
    ) throws Exception
    {
        return GetRecommendList(
                _gender,
                _age,
                _occupation,
                ""
        );
    }

    @Override
    public ArrayList<Score> GetRecommendList(
            String _gender,
            String _age,
            String _occupation,
            String _categories
    ) throws Exception
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
    public ArrayList<Score> GetRecommendList(
            String _title,
            String _limit
    ) throws Exception
    {
        var title = GetMovieFromTitle(_title);
        var limit = ConvertLimit(_limit);

        return GetScoreListByMovie(
                title,
                limit
        );
    }

    @Override
    public ArrayList<Movie.Genre> GetCategoryList( String genreText ) throws Exception
    {
        if ( genreText == null )
        {
            throw new Exception("Invalid genre string");
        }

        if ( genreText.isEmpty() )
        {
            return new ArrayList<>();
        }

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
        if ( _title == null || _title.isEmpty() )
        {
            throw new Exception("Invalid title string");
        }

        /*
        curl http://localhost:8080/movies/recommendations.html?title=Toy_Story_\(1995\)
        형태로 받을 때 "Toy Story" 형태로 바로 받을 수 없음.
        '_' 나 '%20' 형태로 변환해야함.
         */
        var b = _title
                .toLowerCase()
                .replaceAll("_", "")
                .replaceAll("\\s+", "");

        var result = dataReader.GetMovieList().stream().filter(movie -> {
            var a = movie.title
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

        try
        {
            var result = Integer.parseInt(_limit);

            if ( result <= 0 )
            {
                throw new Exception("Limit must be positive integer greater than zero");
            }

            return result;
        }
        catch ( Exception exception )
        {
            throw new Exception("Invalid limit string");
        }
    }

    @Override
    public ArrayList<Score> GetScoreListByUser(
            User.Gender gender,
            User.Age age,
            User.Occupation occupation,
            ArrayList<Movie.Genre> genreList
    ) throws Exception
    {
        try{
            System.out.println("Info: Loading data ...");

            var userList = dataReader.GetUserList();
            var movieList = dataReader.GetMovieList();
            var ratingList = dataReader.GetRatingList();

            System.out.println("Info: Preprocessing ...");

            // 1. 조건에 해당하는 유저 필터
            var filteredUserList = GetFilteredUserList(
                    gender,
                    age,
                    occupation,
                    userList
            );

            // 2. 조건에 해당하는 영화 필터 (최소 평가 개수 필터)
            var filteredMovieList = GetFilteredMovieList(
                    genreList,
                    movieList,
                    ratingList
            );

            // 3. 상위 최대 N명 유저 선택
            filteredUserList = SelectFilteredUser(
                    ratingList,
                    filteredUserList,
                    filteredMovieList
            );

            // 4. 해당유저-해당영화 -> 평점 예측
            var predictList = GetPredictList(
                    filteredUserList,
                    filteredMovieList,
                    ratingList
            );

            // 5. Score 리스트 작성
            var scoreList = ToScoreList(
                    filteredMovieList,
                    predictList
            );

            // 6. 내림차순 정렬 및 상위 10개 선택
            var result = SortingTopList(scoreList);

            // 7. poster 추가?
            for(var ith : result ){
                ith.poster = dataReader.GetPoster(ith.movie.movieId);
            }

            System.out.println("Info: Done");

            return result;
        }
        catch (Exception e){
            throw new Exception("Error in GetScoreListByUser : " + e.getMessage());
        }
    }

    public ArrayList<Score> GetScoreListByUserAll(
            User.Gender gender,
            User.Age age,
            User.Occupation occupation,
            ArrayList<Movie.Genre> genreList
    ) throws Exception
    {
        try{
            System.out.println("Info: Loading data ...");

            var userList = dataReader.GetUserList();
            var movieList = dataReader.GetMovieList();
            var ratingList = dataReader.GetRatingList();

            System.out.println("Info: Preprocessing ...");

            // 1. 조건에 해당하는 유저 필터
            var filteredUserList = GetFilteredUserList(
                    gender,
                    age,
                    occupation,
                    userList
            );

            // 2. 조건에 해당하는 영화 필터 (최소 평가 개수 필터)
            var filteredMovieList = GetFilteredMovieList(
                    genreList,
                    movieList,
                    ratingList
            );

            // 3. 상위 최대 N명 유저 선택
            filteredUserList = SelectFilteredUser(
                    ratingList,
                    filteredUserList,
                    filteredMovieList
            );

            // 4. 해당유저-해당영화 -> 평점 예측
            var predictList = GetPredictList(
                    filteredUserList,
                    filteredMovieList,
                    ratingList
            );

            // 5. Score 리스트 작성
            var scoreList = ToScoreList(
                    filteredMovieList,
                    predictList
            );

            // 7. poster 추가?
            for(var ith : scoreList ){
                ith.poster = dataReader.GetPoster(ith.movie.movieId);
            }

            //다른점: 전체 score return.
            scoreList.sort(
                    (o1, o2) -> Double.compare(o2.score, o1.score)
            );

            System.out.println("Info: Done");

            return scoreList;
        }
        catch (Exception e){
            throw new Exception("Error in GetScoreListByUser : " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Score> GetScoreListByMovie(
            Movie movie,
            int limit
    ) throws Exception
    {
        try{
            System.out.println("Info: Loading data ...");

            var userList = dataReader.GetUserList();
            System.out.println("Info: user load success ...");
            var movieList = dataReader.GetMovieList();
            System.out.println("Info: movie load success ...");
            var ratingList = dataReader.GetRatingList();
            System.out.println("Info: rating load success ...");

            System.out.println("Info: Preprocessing ...");

            // 0. 주어진 영화 제외
            movieList.removeIf(
                    _movie -> _movie.movieId == movie.movieId
            );

            // 1. 전체유저-주어진영화 -> 평점 예측
            var predictList = GetPredictList(
                    userList,
                    Collections.singletonList(movie),
                    ratingList
            );

            // 2. 전체 영화 필터 (최소 평가 개수 필터)
            var filteredMovieList = GetFilteredMovieList(
                    new ArrayList<>(),
                    movieList,
                    ratingList
            );

            // 3. 높게 평가한 상위 최대 N개 선택
            predictList = SelectPredict(
                    predictList,
                    filteredMovieList
            );

            // 4-1. 유저 리스트 매핑
            var selectedUserList = predictList
                    .stream()
                    .map(rating -> {
                        var user = new User();
                        user.userId = rating.user();

                        return user;
                    }).collect(Collectors.toList());

            // 4-2. 해당유저-해당영화 -> 평점 예측
            predictList = GetPredictList(
                    selectedUserList,
                    filteredMovieList,
                    ratingList
            );

            // 5. Score 리스트 작성
            var scoreList = ToScoreList(
                    filteredMovieList,
                    predictList
            );

            // 6. 동일 장르 영화 상위 최대 절반(반올림) 선택
            //    + 나머지 다른 장르 상위 선택
            //    (동일 장르 > 평점 순으로 정렬)
            var result = GetCandidateScoreList(
                    movie,
                    limit,
                    scoreList
            );

            // 7. poster 추가?
            for(var ith : result ){
                ith.poster = dataReader.GetPoster(ith.movie.movieId);
            }

            System.out.println("Info: Done");

            return result;
        }
        catch (Exception e){
            throw new Exception("Error in GetScoreListByMovie : " + e.getMessage());
        }
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

    private List<Rating> SelectPredict(
            List<Rating> predictList,
            List<Movie> filteredMovieList
    )
    {
        var maxUserCount = MAX_PAIR_COUNT / filteredMovieList.size();

        if ( predictList.size() > maxUserCount )
        {
            predictList = predictList
                    .stream()
                    .sorted(
                            Comparator.comparingDouble(Rating::rating)
                                    .reversed()
                    ).limit(maxUserCount)
                    .collect(Collectors.toList());
        }

        return predictList;
    }

    private List<Movie> GetFilteredMovieList(
            List<Movie.Genre> genreList,
            List<Movie> movieList,
            List<Rating> ratingList
    ) throws Exception
    {
        var max = movieList.get(movieList.size() - 1).movieId;

        var ratingCount = new int[max + 1];
        Arrays.fill(ratingCount, 0);

        ratingList.forEach(rating -> {
            var movieId = rating.product();

            if ( movieId > max ) return;

            ++ratingCount[movieId];
        });

        var result = movieList
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

        if ( result.isEmpty() )
        {
            throw new Exception("No movies to recommend");
        }

        return result;
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

        var linkList = dataReader.GetLinkList();

        for (Link link : linkList) {
            var movieId = link.movieId;

            if (movieId > max) continue;

            var score = scoreFullList.get(movieId);

            if (score.movie == null) continue;

            score.link = link;
            /*
            try {
                var tmpPoster = dataReader.GetPoster(movieId);
                if(tmpPoster.equals(Optional.empty())){
                    var p = new Poster();
                    p.movID = movieId;
                    p.posterLink = "";
                    score.poster = p;
                }
                else score.poster = tmpPoster;
            } catch (Exception e) {
                throw new Exception("ToScoreList error: error in adding poster + " + e.getMessage());
            }

             */

            scoreList.add(score);
        }


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

    private ArrayList<Score> GetCandidateScoreList(
            Movie movie,
            int limit,
            ArrayList<Score> scoreList
    )
    {
        var candidateScoreList = new ArrayList<Score>();

        AddCandidateWithGenres(
                movie,
                limit,
                scoreList,
                candidateScoreList
        );

        AddCandidateWithoutGenres(
                movie,
                limit,
                scoreList,
                candidateScoreList
        );

        return candidateScoreList;
    }

    private void AddCandidateWithGenres(
            Movie movie,
            int limit,
            ArrayList<Score> scoreList,
            ArrayList<Score> candidateScoreList
    )
    {
        final var MAX = (int)Math.ceil(limit / 2.0);

        var maximumDifference = 0;

        while ( candidateScoreList.size() < MAX )
        {
            var _maximumDifference = maximumDifference;

            var candidateList = scoreList
                    .stream()
                    .filter(score -> {
                        var total = score.movie.genres.size() + movie.genres.size();
                        var count = score.movie.genres
                                .stream()
                                .filter(movie.genres::contains)
                                .count();

                        if ( count == 0 )
                        {
                            return false;
                        }

                        var difference = total - (2 * count);

                        return (difference == _maximumDifference);
                    }).sorted(
                            (o1, o2) -> Double.compare(o2.score, o1.score)
                    ).limit(MAX - candidateScoreList.size())
                    .collect(Collectors.toList());

            candidateScoreList.addAll(candidateList);

            ++maximumDifference;
        }
    }

    private void AddCandidateWithoutGenres(
            Movie movie,
            int limit,
            ArrayList<Score> scoreList,
            ArrayList<Score> candidateScoreList)
    {
        var candidateList = scoreList
                .stream()
                .filter(
                        score -> score.movie.genres
                                .stream()
                                .noneMatch(
                                        genre -> movie.genres.contains(genre)
                                )
                ).sorted(
                        (o1, o2) -> Double.compare(o2.score, o1.score)
                )
                .limit(limit - candidateScoreList.size())
                .collect(Collectors.toList());

        candidateScoreList.addAll(candidateList);
    }
}

