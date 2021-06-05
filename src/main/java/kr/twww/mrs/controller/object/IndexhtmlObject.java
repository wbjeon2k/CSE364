package kr.twww.mrs.controller.object;

import java.util.ArrayList;

public class IndexhtmlObject {
    private ArrayList<Recommendation> Top10all;

    public IndexhtmlObject(){
        Top10all = new ArrayList<>();
    }

    public IndexhtmlObject( ArrayList<Recommendation> Top10all){
        this.Top10all = Top10all;
    }

    public void setTop10all(ArrayList<Recommendation> top10all) {
        Top10all = top10all;
    }

    public ArrayList<Recommendation> getTop10all() {
        return Top10all;
    }
}
