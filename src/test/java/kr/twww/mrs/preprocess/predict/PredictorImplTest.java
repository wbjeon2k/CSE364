package kr.twww.mrs.preprocess.predict;

import kr.twww.mrs.data.DataReaderImpl;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkContext;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.rdd.RDD;
import org.junit.After;
import org.junit.Test;
import scala.Tuple2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PredictorImplTest
{
    private final PredictorImpl predictor = new PredictorImpl(new DataReaderImpl());

    @After
    public void tearDown()
    {
        predictor.Close();
    }

    @Test
    public void TestLoadModel()
    {
        new MockUp<PredictorImpl>() {
            @Mock
            public String GetChecksum()
            {
                return "TEST";
            }
        };

        new MockUp<PredictorImpl>() {
            @Mock
            public String GetSavedChecksum()
            {
                return "X";
            }
        };

        assertFalse(predictor.LoadModel());

        new MockUp<PredictorImpl>() {
            @Mock
            public String GetSavedChecksum()
            {
                return null;
            }
        };

        assertFalse(predictor.LoadModel());

        new MockUp<PredictorImpl>() {
            @Mock
            public String GetSavedChecksum()
            {
                return "TEST";
            }
        };

        new MockUp<MatrixFactorizationModel>() {
            @Mock
            public void $init( int rank, RDD<Tuple2<Object, double[]>> userFeatures, RDD<Tuple2<Object, double[]>> productFeatures ) {}

            @Mock
            public MatrixFactorizationModel load( SparkContext var0, String var1 )
            {
                return new MatrixFactorizationModel(0, null, null);
            }
        };

        new MockUp<Files>() {
            @Mock
            public boolean isDirectory( Path path, LinkOption... options )
            {
                return false;
            }
        };

        assertFalse(predictor.LoadModel());

        new MockUp<Files>() {
            @Mock
            public boolean isDirectory( Path path, LinkOption... options )
            {
                return true;
            }
        };

        assertTrue(predictor.LoadModel());

        new MockUp<MatrixFactorizationModel>() {
            @Mock
            public MatrixFactorizationModel load( SparkContext var0, String var1 )
            {
                return null;
            }
        };

        assertFalse(predictor.LoadModel());
    }

    @Test
    public void TestCreateModel()
    {
        new MockUp<MatrixFactorizationModel>() {
            @Mock
            public void $init( int rank, RDD<Tuple2<Object, double[]>> userFeatures, RDD<Tuple2<Object, double[]>> productFeatures ) {}

            @Mock
            public void save( SparkContext sc, String path ) {}
        };

        new MockUp<ALS>() {
            @Mock
            public MatrixFactorizationModel train( RDD<Rating> var0, int var1, int var2, double var3 )
            {
                return new MatrixFactorizationModel(0, null, null);
            }
        };

        new Expectations(predictor) {{
            predictor.GetChecksum();
            result = "";
        }};

        new Expectations(predictor) {{
            predictor.SaveChecksum(anyString);
        }};

        assertTrue(
                predictor.CreateModel(new ArrayList<>())
        );

        new MockUp<ALS>() {
            @Mock
            public MatrixFactorizationModel train( RDD<Rating> var0, int var1, int var2, double var3 )
            {
                return null;
            }
        };

        assertFalse(
                predictor.CreateModel(new ArrayList<>())
        );
    }

    @Test
    public void TestDeleteModel()
    {
        new MockUp<FileUtils>() {
            @Mock
            public void deleteDirectory( File directory ) throws IOException
            {
                throw new IOException();
            }
        };

        try
        {
            var method = PredictorImpl
                    .class
                    .getDeclaredMethod("DeleteModel");
            method.setAccessible(true);

            method.invoke(predictor);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void TestGetPredictList()
    {
        assertNull(
                predictor.GetPredictList(
                        null,
                        null
                )
        );
    }

    @Test
    public void TestGetChecksum()
    {
        new MockUp<FileInputStream>() {
            @Mock
            public void $init( String name ) throws FileNotFoundException
            {
                throw new FileNotFoundException();
            }
        };

        assertNull(predictor.GetChecksum());
    }

    @Test
    public void TestGetSavedChecksum()
    {
        new MockUp<Files>() {
            @Mock
            public boolean exists( Path path, LinkOption... options )
            {
                return false;
            }
        };

        assertNull(predictor.GetSavedChecksum());

        new MockUp<Files>() {
            @Mock
            public boolean exists( Path path, LinkOption... options )
            {
                return true;
            }
        };

        new MockUp<DataReaderImpl>() {
            @Mock
            public String ReadTextFromFile( String path )
            {
                return "TEST";
            }
        };

        assertEquals(predictor.GetSavedChecksum(), "TEST");
    }

    @Test
    public void TestSaveChecksum()
    {
        var original = new File("./data/checksum");
        var temp = new File("./data/_checksum");

        new MockUp<File>() {
            @Mock
            public void $init( String pathname ) throws Exception
            {
                throw new Exception();
            }
        };

        try
        {
            if ( Files.exists(original.toPath()) )
            {
                FileUtils.moveFile(
                        original,
                        temp
                );
            }

            predictor.SaveChecksum(null);

            assertFalse(Files.exists(original.toPath()));

            if ( Files.exists(temp.toPath()) )
            {
                FileUtils.moveFile(
                        temp,
                        original
                );
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail();
        }
    }
}