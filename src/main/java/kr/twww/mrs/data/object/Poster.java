package kr.twww.mrs.data.object;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Poster {

    @Id
    public int movID;
    public String posterLink;

    public Poster(){
        movID = 0;
        posterLink = "";
    }

    public Poster(int movID, String posterLink){
        this.movID = movID;
        this.posterLink = posterLink;
    }

    public int getMovID() {
        return movID;
    }
    public String getPosterLink(){
        return posterLink;
    }

    public void setMovID(int movID) {
        this.movID = movID;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }
}
