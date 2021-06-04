package kr.twww.mrs.data.object;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Poster {

    @Id
    public int movID;
    public String posterLink;

    public Poster(int movID, String posterLink){
        this.movID = movID;

        if(posterLink != null) this.posterLink = posterLink;
        else this.posterLink = "";
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
        if(posterLink != null) this.posterLink = posterLink;
        else this.posterLink = "";
    }
}
