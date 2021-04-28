package kr.twww.mrs.data.object;

import java.util.ArrayList;

public class Movie
{
    public enum Genre
    {
        ACTION,
        ADVENTURE,
        ANIMATION,
        CHILDREN_S,
        COMEDY,
        CRIME,
        DOCUMENTARY,
        DRAMA,
        FANTASY,
        FILM_NOIR,
        HORROR,
        MUSICAL,
        MYSTERY,
        ROMANCE,
        SCI_FI,
        THRILLER,
        WAR,
        WESTERN
    }

    public int movieId;
    public String title;
    public ArrayList<Genre> genres;

    public static Genre ConvertGenre( String _genre )
    {

        if(_genre.equals("Children's")){
            return Genre.CHILDREN_S;
        }
        else if(_genre.equals("Film-Noir")){
            return Genre.FILM_NOIR;
        }
        else if(_genre.equals("Sci-Fi")){
            return Genre.SCI_FI;
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
