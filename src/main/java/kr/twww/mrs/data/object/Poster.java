package kr.twww.mrs.data.object;

import com.opencsv.bean.CsvBindByPosition;

public class Poster {

    @CsvBindByPosition(position = 0)
    public int movID;
    @CsvBindByPosition(position = 1)
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
