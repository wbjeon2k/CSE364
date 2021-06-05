package kr.twww.mrs.controller.object;

import java.util.ArrayList;

public class IndexhtmlObject {
    private ArrayList<Recommendation> Top10all;
    private ArrayList<Recommendation> Top10action;
    private ArrayList<Recommendation> Top10Comedy;
    private ArrayList<Recommendation> Top10Adventure;
    private ArrayList<Recommendation> Top10Drama;


    public IndexhtmlObject(){
        Top10all = new ArrayList<>();
        Top10action = new ArrayList<>();
    }

    public IndexhtmlObject( ArrayList<Recommendation> Top10all){
        this.Top10all = Top10all;
        Top10action = new ArrayList<>();
    }

    public IndexhtmlObject( ArrayList<Recommendation> Top10all,
                            ArrayList<Recommendation> Top10action,
                            ArrayList<Recommendation> Top10Comedy,
                            ArrayList<Recommendation> Top10Adventure,
                            ArrayList<Recommendation> Top10Drama
                            ){
        this.Top10all = Top10all;
        this.Top10action = Top10action;
        this.Top10Comedy = Top10Comedy;
        this.Top10Adventure = Top10Adventure;
        this.Top10Drama = Top10Drama;
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
