package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.DataReaderImpl;
import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.Rating;
import kr.twww.mrs.data.object.User;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PreprocessorImplTest
{
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    private final DataReaderImpl dataReader = new DataReaderImpl();
    private final PreprocessorImpl preprocessor = new PreprocessorImpl();

    private void ClearChecksumAndModel()
    {
        try
        {
            Files.deleteIfExists(Paths.get("./data/checksum"));
            FileUtils.deleteDirectory(new File("./data/model"));
        }
        catch ( IOException e )
        {
//                e.printStackTrace();
        }
    }

    @Test
    public void TestGetRecommendList()
    {
        var result = preprocessor.GetRecommendList(
                null,
                null,
                null
        );

        assertNull(result);
    }

    @Test
    public void TestGetCategoryList()
    {
        var result = preprocessor.GetCategoryList(null);

        assertNull(result);
    }

    @Test
    public void TestGetCategoryList2()
    {
        var result = preprocessor.GetCategoryList("");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void TestGetCategoryList3()
    {
        var result = preprocessor.GetCategoryList("action");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Movie.Genre.ACTION, result.get(0));
    }

    @Test
    public void TestGetCategoryList4()
    {
        var result = preprocessor.GetCategoryList("TEST");

        assertNull(result);
    }

    @Test
    public void TestGetScoreList()
    {
        assertNull(preprocessor.GetScoreList(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null));
        assertNull(preprocessor.GetScoreList(
                User.Gender.UNKNOWN,
                null,
                null,
                null,
                null,
                null,
                null,
                null));
        assertNull(preprocessor.GetScoreList(
                User.Gender.UNKNOWN,
                User.Age.UNKNOWN,
                null,
                null,
                null,
                null,
                null,
                null));
        assertNull(preprocessor.GetScoreList(
                User.Gender.UNKNOWN,
                User.Age.UNKNOWN,
                User.Occupation.UNKNOWN,
                null,
                null,
                null,
                null,
                null));
        assertNull(preprocessor.GetScoreList(
                User.Gender.UNKNOWN,
                User.Age.UNKNOWN,
                User.Occupation.UNKNOWN,
                new ArrayList<>(),
                null,
                null,
                null,
                null));
        assertNull(preprocessor.GetScoreList(
                User.Gender.UNKNOWN,
                User.Age.UNKNOWN,
                User.Occupation.UNKNOWN,
                new ArrayList<>(),
                new ArrayList<>(),
                null,
                null,
                null));
        assertNull(preprocessor.GetScoreList(
                User.Gender.UNKNOWN,
                User.Age.UNKNOWN,
                User.Occupation.UNKNOWN,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                null,
                null));
        assertNull(preprocessor.GetScoreList(
                User.Gender.UNKNOWN,
                User.Age.UNKNOWN,
                User.Occupation.UNKNOWN,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                null));
        assertNotNull(preprocessor.GetScoreList(
                User.Gender.UNKNOWN,
                User.Age.UNKNOWN,
                User.Occupation.UNKNOWN,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()));
    }

    @Test
    public void TestGetScoreList2()
    {
        var userList = new ArrayList<User>();
        var movieList = new ArrayList<Movie>();
        var ratingList = new ArrayList<Rating>();
        var linkList = new ArrayList<Link>();

        userList.add(new User());
        movieList.add(new Movie());
        ratingList.add(new Rating());
        linkList.add(new Link());

        assertNotNull(preprocessor.GetScoreList(
                User.Gender.UNKNOWN,
                User.Age.UNKNOWN,
                User.Occupation.UNKNOWN,
                new ArrayList<>(),
                userList,
                movieList,
                ratingList,
                linkList));
    }

    @Test
    public void TestGetScoreList3()
    {
        var genreList = new ArrayList<Movie.Genre>();
        genreList.add(Movie.Genre.ACTION);
        genreList.add(Movie.Genre.COMEDY);

        var result = preprocessor.GetScoreList(
                User.Gender.FEMALE,
                User.Age.BETWEEN_25_34,
                User.Occupation.COLLEGE_OR_GRAD_STUDENT,
                genreList,
                dataReader.GetUserList(),
                dataReader.GetMovieList(),
                dataReader.GetRatingList(),
                dataReader.GetLinkList());

        assertNotNull(result);
        assertEquals(10, result.size());
        assertTrue(result.get(0).score >= result.get(1).score);
        assertTrue(result.get(1).score >= result.get(2).score);
        assertTrue(result.get(2).score >= result.get(3).score);
        assertTrue(result.get(3).score >= result.get(4).score);
        assertTrue(result.get(4).score >= result.get(5).score);
        assertTrue(result.get(5).score >= result.get(6).score);
        assertTrue(result.get(6).score >= result.get(7).score);
        assertTrue(result.get(7).score >= result.get(8).score);
        assertTrue(result.get(8).score >= result.get(9).score);
    }

    @Test
    public void TestGetScoreList4()
    {
        var genreList = new ArrayList<Movie.Genre>();
        genreList.add(Movie.Genre.ACTION);
        genreList.add(Movie.Genre.COMEDY);

        var result = preprocessor.GetScoreList(
                User.Gender.FEMALE,
                User.Age.BETWEEN_25_34,
                User.Occupation.COLLEGE_OR_GRAD_STUDENT,
                genreList,
                dataReader.GetUserList(),
                new ArrayList<>(),
                dataReader.GetRatingList(),
                dataReader.GetLinkList());

        var result2 = preprocessor.GetScoreList(
                User.Gender.FEMALE,
                User.Age.BETWEEN_25_34,
                User.Occupation.COLLEGE_OR_GRAD_STUDENT,
                genreList,
                dataReader.GetUserList(),
                dataReader.GetMovieList(),
                dataReader.GetRatingList(),
                new ArrayList<>());

        assertNull(result);
        assertNull(result2);
    }

    // TODO: 테스트 작성중
    @Ignore
    @Test
    public void TestSaveChecksum()
    {
        expectedException.expect(IOException.class);

        try
        {
            var method = preprocessor.getClass().getDeclaredMethod("SaveChecksum", String.class);
            method.setAccessible(true);

            method.invoke(preprocessor, "");
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}