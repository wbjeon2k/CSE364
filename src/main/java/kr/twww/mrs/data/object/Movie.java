package kr.twww.mrs.data.object;

import java.util.ArrayList;
import org.springframework.data.annotation.*;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Movie
{
    public enum
    Genre
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

    @Id
    public int movieId;
    public String title;
    public ArrayList<Genre> genres;

    public Movie(){}

    public Movie(int mov_id, String title, String genrelist) throws Exception {
        this.title = title;
        this.movieId = mov_id;
        this.genres = getGenreListInit(genrelist);
    }

    private ArrayList<Movie.Genre> getGenreListInit( String genresText ) throws Exception
    {
        var genreList = new ArrayList<Movie.Genre>();
        var splitGenre = genresText.split("\\|");

        for ( String j : splitGenre )
        {
            genreList.add(Movie.ConvertGenre(j));
        }

        return genreList;
    }

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
        final String[] GENRE_TEXT = new String[] {
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


        var genreTextList = new ArrayList<String>();

        genres.forEach(genre ->
                genreTextList.add(
                        GENRE_TEXT[genre.ordinal()]
                )
        );

        return String.join("|", genreTextList);
    }

}
