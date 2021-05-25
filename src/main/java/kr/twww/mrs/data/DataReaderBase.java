package kr.twww.mrs.data;

import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.User;
import org.apache.spark.mllib.recommendation.Rating;

import java.util.ArrayList;

public abstract class DataReaderBase
{
    public abstract ArrayList<User> ToUserList( String textList ) throws Exception;
    public abstract ArrayList<Movie> ToMovieList( String textList ) throws Exception;
    public abstract ArrayList<Rating> ToRatingList( String textList ) throws Exception;
    public abstract ArrayList<Link> ToLinkList( String textList ) throws Exception;
}
