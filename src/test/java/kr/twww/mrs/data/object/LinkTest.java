package kr.twww.mrs.data.object;

import org.junit.Test;

import static org.junit.Assert.*;

public class LinkTest
{
    @Test
    public void TestGetURL()
    {
        var link = new Link();
        link.imdbId = "TEST";

        assertEquals("http://www.imdb.com/title/ttTEST", link.GetURL());
    }
}