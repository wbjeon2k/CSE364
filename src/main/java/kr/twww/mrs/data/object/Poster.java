package kr.twww.mrs.data.object;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;


public class Poster {

    public int movID;
    public String posterLink;

    public String getPosterLink() {
        return posterLink;
    }
}
