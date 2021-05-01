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

    public static Genre ConvertGenre( String _genre ){
        _genre = _genre.replaceAll("[^a-zA-Z0-9]", "");
        _genre = _genre.toUpperCase();

        for ( var i : Genre.values() )
        {
            var genre = i.name();
            genre = genre.replaceAll("[^a-zA-Z0-9]", "");
            genre = genre.toUpperCase();

            if ( genre.equals(_genre) )
            {
                return i;
            }
        }

        return null;
    }
}


