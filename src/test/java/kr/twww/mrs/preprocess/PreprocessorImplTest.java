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
}