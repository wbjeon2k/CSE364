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

        if(_genre.equals("Children's")){
            return Genre.Children_s;
        }
        else if(_genre.equals("Film-Noir")){
            return Genre.Film_Noir;
        }
        else if(_genre.equals("Sci-Fi")){
            return Genre.Sci_Fi;
        }
        else {
            try
            {
                return Genre.valueOf(_genre);
            }
            catch ( IllegalArgumentException e )
            {
                e.printStackTrace();

                return null;
            }
        }

    }
}
