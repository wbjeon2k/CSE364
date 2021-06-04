package kr.twww.mrs.data.object;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;


public class Poster {

    public int movID;
    public String posterLink;

    public int getMovID() {
        return movID;
    }

    public void setMovID(int movID) {
        this.movID = movID;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }
}
