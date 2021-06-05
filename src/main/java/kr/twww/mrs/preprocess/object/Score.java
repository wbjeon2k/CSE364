package kr.twww.mrs.preprocess.object;

import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.Poster;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;

public class Score
{
    public Movie movie;
    public Link link;
    public double score;
    public Poster poster;

    public Score()
    {
        movie = new Movie();
        movie.title = "";
        movie.genres.add(Movie.Genre.ADVENTURE);
        link = new Link();
        link.imdbId = "0000000";
        poster = new Poster();
        poster.posterLink = "00000000";
        score = 0.0;
    }
}
