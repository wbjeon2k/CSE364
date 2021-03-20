package kr.twww.mrs.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataLoaderTest
{
    DataLoaderImpl dataLoader;

    @Before
    public void setUp() throws Exception
    {
        dataLoader = new DataLoaderImpl();
    }

    @After
    public void tearDown() throws Exception
    {
        dataLoader = null;
    }

    /**
     * 아직 구현되지 않아 null을 반환하는 상태
     * 이후에 구현이 되고 테스트가 실패되어야 함
     */
    @Test
    public void getUserList()
    {
        var userList = dataLoader.GetUserList();
        assertNull(userList);
    }

    /**
     * 아직 구현되지 않아 null을 반환하는 상태
     * 이후에 구현이 되고 테스트가 실패되어야 함
     */
    @Test
    public void getMovieList()
    {
        var movieList = dataLoader.GetMovieList();
        assertNull(movieList);
    }

    /**
     * 아직 구현되지 않아 null을 반환하는 상태
     * 이후에 구현이 되고 테스트가 실패되어야 함
     */
    @Test
    public void getRatingList()
    {
        var ratingList = dataLoader.GetRatingList();
        assertNull(ratingList);
    }
}