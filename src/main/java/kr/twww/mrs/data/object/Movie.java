package kr.twww.mrs.data.object;

import java.util.ArrayList;
import org.springframework.data.annotation.*;

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

    private final String[] GENRE_TEXT = new String[] {
            "Action",
            "Adventure",
            "Animation",
            "Children's",
            "Comedy",
            "Crime",
            "Documentary",
            "Drama",
            "Fantasy",
            "Film-Noir",
            "Horror",
            "Musical",
            "Mystery",
            "Romance",
            "Sci-Fi",
            "Thriller",
            "War",
            "Western"
    };

    @Id
    public int movieId;

    public String title;
    public ArrayList<Genre> genres;

    public static Genre ConvertGenre( String _genre ) throws Exception
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

        throw new Exception("Invalid genre string");
    }

    public String GetGenresText()
    {
        var genreTextList = new ArrayList<String>();

        genres.forEach(genre ->
                genreTextList.add(
                        GENRE_TEXT[genre.ordinal()]
                )
        );

        return String.join("|", genreTextList);
    }
}
