package kr.twww.mrs.data;

import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.User;
import mockit.Expectations;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataReaderImplTest
{
    @Autowired
    private DataReaderImpl dataReader;

    @Test
    public void TestGetPathFromDataType() throws Exception
    {
        try
        {
            dataReader.GetPathFromDataType(null);
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

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
    public void TestReadTextFromFile() throws Exception
    {
        try
        {
            dataReader.ReadTextFromFile(null);
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        try
        {
            dataReader.ReadTextFromFile("");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        assertEquals(
                "TEST",
                dataReader.ReadTextFromFile("./data/test.dat")
        );

        try
        {
            dataReader.ReadTextFromFile("xExRxRxOxRx");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }
    }

    /*
    @PostConstruct 때문에 datareader 가 생성되어서 정상작동함.
    정상작동 안한다는 가정이 붙은 이 테스트는 통과할 수 없음. 수정 예정.
     */
    @Ignore
    @Test
    public void TestGetList() throws Exception
    {
        new Expectations(dataReader) {{
            dataReader.ReadTextFromFile(anyString);
            result = "";
        }};

        try
        {
            dataReader.GetUserList();
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        try
        {
            dataReader.GetMovieList();
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        try
        {
            dataReader.GetRatingList();
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        try
        {
            dataReader.GetLinkList();
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }
    }

    @Test
    public void TestToUserList() throws Exception
    {
        try
        {
            dataReader.ToUserList("");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        try
        {
            dataReader.ToUserList(" ");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        var result = dataReader.ToUserList("0::M::17::0::TEST");
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).userId);
        assertEquals(User.Gender.MALE, result.get(0).gender);
        assertEquals(User.Age.UNDER_18, result.get(0).age);
        assertEquals(User.Occupation.OTHER, result.get(0).occupation);
        assertEquals("TEST", result.get(0).zipCode);
    }

    @Test
    public void TestToMovieList() throws Exception
    {
        try
        {
            System.out.println("TestToMovieList test 1");
            dataReader.ToMovieList("");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        try
        {
            System.out.println("TestToMovieList test 2");
            dataReader.ToMovieList(" ");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }


        var result = dataReader.ToMovieList("0::TEST::action");
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).movieId);
        assertEquals("TEST", result.get(0).title);
        assertEquals(1, result.get(0).genres.size());
        assertEquals(Movie.Genre.ACTION, result.get(0).genres.get(0));

        try
        {
            System.out.println("TestToMovieList test 3");
            dataReader.ToMovieList("0::TEST::aXcXtXiXoXn");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }
    }

    @Test
    public void TestToRatingList() throws Exception
    {
        try
        {
            dataReader.ToRatingList("");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        try
        {
            dataReader.ToRatingList(" ");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        var result = dataReader.ToRatingList("0::1::2::3");
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).user());
        assertEquals(1, result.get(0).product());
        assertEquals(2, result.get(0).rating(), 0.0);
    }

    @Test
    public void TestToLinkList() throws Exception
    {
        try
        {
            dataReader.ToLinkList("");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        try
        {
            dataReader.ToLinkList(" ");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        var result = dataReader.ToLinkList("0::TEST");
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).movieId);
        assertEquals("TEST", result.get(0).imdbId);
    }
}