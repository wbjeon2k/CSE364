package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.DataReader;
import kr.twww.mrs.data.Movie;
import kr.twww.mrs.data.Rating;
import kr.twww.mrs.data.User;

import java.util.ArrayList;

public abstract class PreprocessorBase
{
    public abstract ArrayList<Movie.Genre> GetGenreList( String genreText );
    public abstract User.Occupation GetOccupation( String occupationText );

    public abstract ArrayList<Rating> GetScoreList(
            ArrayList<Movie.Genre> genreList,
            User.Occupation occupation,
            ArrayList<User> userList,
            ArrayList<Movie> movieList,
            ArrayList<Rating> ratingList
    );
}
