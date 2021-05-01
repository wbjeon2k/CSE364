package kr.twww.mrs.data.object;

public class Link
{
    public int movieId;
    public String imdbId;

    public String GetURL()
    {
        return "http://www.imdb.com/title/tt" + imdbId;
    }
}
