package kr.twww.mrs.preprocess.webquery;

import kr.twww.mrs.preprocess.object.Score;

import java.io.Serializable;

public class MovieJson {
    private String title;
    private String genre;
    private String imdb;

    public MovieJson(){
        this.title = "T";
        this.genre = "G";
        this.imdb = "I";
    }

    public MovieJson(Score S){
        this.title = S.movie.title;
        this.genre = S.movie.genres.toString();
        this.imdb = S.link.GetURL();
    }

    public String getGenre() {
        return genre;
    }

    public String getImdb() {
        return imdb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }
}
