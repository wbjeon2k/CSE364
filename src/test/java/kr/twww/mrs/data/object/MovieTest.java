package kr.twww.mrs.data.object;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MovieTest
{
    @Test
    public void TestConvertGenre() throws Exception
    {
        assertEquals(Movie.Genre.ACTION, Movie.ConvertGenre("action"));
        assertEquals(Movie.Genre.ACTION, Movie.ConvertGenre("AcTiOn"));
        assertEquals(Movie.Genre.ACTION, Movie.ConvertGenre("ACTION"));
        assertEquals(Movie.Genre.CHILDREN_S, Movie.ConvertGenre("children's"));

        try
        {
            Movie.ConvertGenre("");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }
    }

    @Test
    public void TestGetGenresText()
    {
        var movie = new Movie();
        movie.genres = new ArrayList<>();
        movie.genres.add(Movie.Genre.ACTION);

        assertEquals("Action", movie.GetGenresText());
    }
}