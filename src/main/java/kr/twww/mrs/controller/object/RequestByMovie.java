package kr.twww.mrs.controller.object;

public class RequestByMovie
{
    private String title;
    private String limit = "10";

    public RequestByMovie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
