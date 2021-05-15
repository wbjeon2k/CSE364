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

        System.out.println("Error: Invalid genre string");
        return null;
    }

    public String GenresToString(){
        String ret = "";
        for (int i=0;i<genres.size();++i){
            ret.concat(genres.get(i).toString());
            if(i != (genres.size() - 1)) ret.concat("|");
        }
        return ret;
    }
}
