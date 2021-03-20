package kr.twww.mrs.data;

import java.util.ArrayList;

public class Movie
{
    public enum Genre
    {
        Action,
        Adventure,
        Animation,
        Children_s,
        Comedy,
        Crime,
        Documentary,
        Drama,
        Fantasy,
        Film_Noir,
        Horror,
        Musical,
        Mystery,
        Romance,
        Sci_Fi,
        Thriller,
        War,
        Western
    }

    public int movieId;
    public String title;
    public ArrayList<Genre> genres;

    public static Genre ConvertGenre( String _genre )
    {
        // TODO
        return null;
    }
}
