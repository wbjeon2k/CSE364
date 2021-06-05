package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.User;
import kr.twww.mrs.preprocess.object.Score;

import java.util.ArrayList;

public abstract class PreprocessorBase
{
    public abstract void PreprocessorImplInit() throws Exception;

    public abstract ArrayList<Movie.Genre> GetCategoryList( String genreText ) throws Exception;

    public abstract Movie GetMovieFromTitle( String _title ) throws Exception;

    public abstract int ConvertLimit( String _limit ) throws Exception;

    public abstract ArrayList<Score> GetScoreListByUser(
            User.Gender gender,
            User.Age age,
            User.Occupation occupation,
            ArrayList<Movie.Genre> genreList
    ) throws Exception;

    public abstract ArrayList<Score> GetScoreListByMovie(
            Movie movie,
            int limit
    ) throws Exception;
}
