package kr.twww.mrs.data;

import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.User;
import mockit.Expectations;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataReaderImplTest
{
    private final DataReaderImpl dataReader = new DataReaderImpl();

    @Test
    public void TestGetPathFromDataType()
    {
        assertNull(dataReader.GetPathFromDataType(null));
        assertEquals("./data/users.dat",
                dataReader.GetPathFromDataType(DataType.USER));
        assertEquals("./data/movies.dat",
                dataReader.GetPathFromDataType(DataType.MOVIE));
        assertEquals("./data/ratings.dat",
                dataReader.GetPathFromDataType(DataType.RATING));
        assertEquals("./data/links.dat",
                dataReader.GetPathFromDataType(DataType.LINK));
    }

    @Test
    public void TestReadTextFromFile()
    {
        assertNull(
                dataReader.ReadTextFromFile(null)
        );

        assertNull(
                dataReader.ReadTextFromFile("")
        );

        assertEquals(
                "TEST",
                dataReader.ReadTextFromFile("./data/test/test.dat")
        );

        assertNull(
                dataReader.ReadTextFromFile("xExRxRxOxRx")
        );
    }

    @Test
    public void TestGetList()
    {
        new Expectations(dataReader) {{
            dataReader.ReadTextFromFile(anyString);
            result = "";
        }};

        var result = dataReader.GetUserList();
        assertNotNull(result);
        assertTrue(result.isEmpty());

        var result2 = dataReader.GetMovieList();
        assertNotNull(result2);
        assertTrue(result2.isEmpty());

        var result3 = dataReader.GetRatingList();
        assertNotNull(result3);
        assertTrue(result3.isEmpty());

        var result4 = dataReader.GetLinkList();
        assertNotNull(result4);
        assertTrue(result4.isEmpty());
    }

    @Test
    public void TestToUserList()
    {
        assertNull(
                dataReader.ToUserList(null)
        );

        var result = dataReader.ToUserList("");
        assertNotNull(result);
        assertTrue(result.isEmpty());

        var result2 = dataReader.ToUserList("0::M::17::0::TEST");
        assertEquals(1, result2.size());
        assertEquals(0, result2.get(0).userId);
        assertEquals(User.Gender.MALE, result2.get(0).gender);
        assertEquals(User.Age.UNDER_18, result2.get(0).age);
        assertEquals(User.Occupation.OTHER, result2.get(0).occupation);
        assertEquals("TEST", result2.get(0).zipCode);
    }


    @Test
    public void TestToMovieList()
    {
        assertNull(
                dataReader.ToMovieList(null)
        );

        var result = dataReader.ToMovieList("");
        assertNotNull(result);

        var result2 = dataReader.ToMovieList("0::TEST::action");
        assertEquals(1, result2.size());
        assertEquals(0, result2.get(0).movieId);
        assertEquals("TEST", result2.get(0).title);
        assertEquals(1, result2.get(0).genres.size());
        assertEquals(Movie.Genre.ACTION, result2.get(0).genres.get(0));

        assertNull(
                dataReader.ToMovieList("0::TEST::aXcXtXiXoXn")
        );
    }

    @Test
    public void TestToRatingList()
    {
        assertNull(
                dataReader.ToRatingList(null)
        );

        var result = dataReader.ToRatingList("");
        assertNotNull(result);
        assertTrue(result.isEmpty());

        var result2 = dataReader.ToRatingList("0::1::2::3");
        assertEquals(1, result2.size());
        assertEquals(0, result2.get(0).user());
        assertEquals(1, result2.get(0).product());
        assertEquals(2, result2.get(0).rating(), 0.0);
    }

    @Test
    public void TestToLinkList()
    {
        assertNull(
                dataReader.ToLinkList(null)
        );

        var result = dataReader.ToLinkList("");
        assertNotNull(result);
        assertTrue(result.isEmpty());

        var result2 = dataReader.ToLinkList("0::TEST");
        assertEquals(1, result2.size());
        assertEquals(0, result2.get(0).movieId);
        assertEquals("TEST", result2.get(0).imdbId);
    }
}