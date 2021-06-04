package kr.twww.mrs.data.object;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Link
{
    @Id
    public int movieId;
    public String imdbId;

    public int getMovieId() {
        return movieId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String GetURL()
    {
        return "http://www.imdb.com/title/tt" + imdbId;
    }
}
