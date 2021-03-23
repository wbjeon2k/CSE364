package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.*;

import java.util.ArrayList;

public class PreprocessorImpl extends PreprocessorBase implements Preprocessor, DataReader
{
    DataReader dataReader;

    public PreprocessorImpl()
    {
        dataReader = new DataReaderImpl();
    }

    @Override
    public ArrayList<Score> GetScoreList( String _category, String _occupation )
    {
        var genreList = GetGenreList(_category);
        var occupation = GetOccupation(_occupation);

        var result = GetScoreList(
                genreList,
                occupation,
                GetUserList(),
                GetMovieList(),
                GetRatingList()
        );

        return result;
    }

    @Override
    public ArrayList<User> GetUserList()
    {
        return dataReader.GetUserList();
    }

    @Override
    public ArrayList<Movie> GetMovieList()
    {
        return dataReader.GetMovieList();
    }

    @Override
    public ArrayList<Rating> GetRatingList()
    {
        return dataReader.GetRatingList();
    }

    @Override
    public ArrayList<Movie.Genre> GetGenreList( String genreText )
    {
        // TODO: 주어진 텍스트를 enum Genre 리스트로 반환
        return null;
    }

    @Override
    public User.Occupation GetOccupation( String occupationText )
    {
        // TODO: 주어진 텍스트를 enum Occupation으로 반환
        return null;
    }

    @Override
    public ArrayList<Score> GetScoreList(
            ArrayList<Movie.Genre> genreList,
            User.Occupation occupation,
            ArrayList<User> userList,
            ArrayList<Movie> movieList,
            ArrayList<Rating> ratingList
    )
    {

        /**
         * TODO:
         * 1. 카테고리(장르)에 해당하는 모든 영화 필터링
         * 2. 해당하는 영화 및 동일한 직업의 유저들의 평가 필터링
         * 3. 영화마다 새로운 Score에 설정 및 해당하는 모든 평가 추가
         * 4. Score 리스트를 반환
         *
         * 참고:
         * +. User.ConvertOccupation()
         * +. Movie.ConvertGenre()
         */
        return null;
    }
}
