package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.User;
import kr.twww.mrs.preprocess.object.Score;

import java.util.ArrayList;

public abstract class PreprocessorBase
{
    public abstract ArrayList<Movie.Genre> GetCategoryList( String genreText );

    public abstract ArrayList<Score> GetScoreList(
            User.Gender gender,
            User.Age age,
            User.Occupation occupation,
            ArrayList<Movie.Genre> genreList
    );

    public abstract ArrayList<Score> GetScoreList_2(
            String Title,
            int limit
    );
}
