package kr.twww.mrs.controller.object;

import java.util.ArrayList;

public class IndexhtmlObject {
    private ArrayList<Recommendation> Top10all;
    private ArrayList<Recommendation> Top10action;


    public IndexhtmlObject(){
        Top10all = new ArrayList<>();
        Top10action = new ArrayList<>();
    }

    public IndexhtmlObject( ArrayList<Recommendation> Top10all){
        this.Top10all = Top10all;
        Top10action = new ArrayList<>();
    }

    public void setTop10action(ArrayList<Recommendation> top10action) {
        Top10action = top10action;
    }

    public void setTop10all(ArrayList<Recommendation> top10all) {
        Top10all = top10all;
    }

    public ArrayList<Recommendation> getTop10action() {
        return Top10action;
    }

    public ArrayList<Recommendation> getTop10all() {
        return Top10all;
    }
}
