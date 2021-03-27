package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.*;

import java.util.ArrayList;

public class PreprocessorImpl extends PreprocessorBase implements Preprocessor
{
    DataReader dataReader;

    public PreprocessorImpl()
    {
        dataReader = new DataReaderImpl();
    }

    @Override
    public ArrayList<Rating> GetScoreList( String _category, String _occupation )
    {
        var genreList = GetGenreList(_category);
        var occupation = GetOccupation(_occupation);

        var result = GetScoreList(
                genreList,
                occupation,
                dataReader.GetUserList(),
                dataReader.GetMovieList(),
                dataReader.GetRatingList()
        );

        return result;
    }

    @Override
    public ArrayList<Movie.Genre> GetGenreList( String genreText )
    {
        // TODO: 주어진 텍스트를 enum Movie.Genre 리스트로 반환
        return null;
    }

    @Override
    public User.Occupation GetOccupation( String occupationText )
    {
        // TODO: 주어진 텍스트를 enum User.Occupation으로 반환
        return null;
    }

    @Override
    public ArrayList<Rating> GetScoreList(
            ArrayList<Movie.Genre> genreList,
            User.Occupation occupation,
            ArrayList<User> userList,
            ArrayList<Movie> movieList,
            ArrayList<Rating> ratingList
    )
    {
        // TODO
        return null;
    }
}
