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
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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

@SuppressWarnings("ConstantConditions")
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class PredictorImplTest
{
    @Autowired
    private PredictorImpl predictor;

    @Before
    public void setUp()
    {
        predictor.destroy();
        predictor.afterPropertiesSet();
    }

    @Test
    public void TestLoadModel() throws Exception
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

        new MockUp<MatrixFactorizationModel>() {
            @Mock
            public MatrixFactorizationModel load( SparkContext var0, String var1 )
            {
                return null;
            }
        };

        assertFalse(predictor.LoadModel());

        new MockUp<MatrixFactorizationModel>() {
            @Mock
            public MatrixFactorizationModel load( SparkContext var0, String var1 )
            {
                return new MatrixFactorizationModel(0, null, null);
            }
        };

        assertTrue(predictor.LoadModel());
        assertTrue(predictor.LoadModel());
    }

    @Test
    public void TestCreateModel() throws Exception
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
        try
        {
            var method = PredictorImpl
                    .class
                    .getDeclaredMethod("DeleteModel");
            method.setAccessible(true);

            new MockUp<Files>() {
                @Mock
                public boolean exists( Path path, LinkOption... options )
                {
                    return false;
                }
            };

            method.invoke(predictor);

            new MockUp<Files>() {
                @Mock
                public boolean exists( Path path, LinkOption... options )
                {
                    return true;
                }

                @Mock
                public boolean isDirectory( Path path, LinkOption... options )
                {
                    return true;
                }
            };

            new MockUp<FileUtils>() {
                @Mock
                public void deleteDirectory( File directory ) {}
            };

            method.invoke(predictor);

            new MockUp<Files>() {
                @Mock
                public boolean isDirectory( Path path, LinkOption... options )
                {
                    return false;
                }

                @Mock
                public void delete( Path path ) {}
            };

            method.invoke(predictor);

            new MockUp<Files>() {
                @Mock
                public void delete( Path path ) throws IOException
                {
                    throw new IOException();
                }
            };

            try
            {
                method.invoke(predictor);
                fail();
            }
            catch ( Exception exception )
            {
                assertTrue(true);
            }
        }
        catch ( Exception e )
        {
            fail();
        }
    }

    @Test
    public void TestGetPredictList()
    {
        try
        {
            predictor.GetPredictList(
                    null,
                    null
            );
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }
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

        try
        {
            predictor.GetChecksum();
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }
    }

    @Test
    public void TestGetSavedChecksum() throws Exception
    {
        new MockUp<Files>() {
            @Mock
            public boolean exists( Path path, LinkOption... options )
            {
                return false;
            }
        };

        assertNull(
                predictor.GetSavedChecksum()
        );

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

            try
            {
                predictor.SaveChecksum(null);
                fail();
            }
            catch ( Exception exception )
            {
                assertTrue(true);
            }

            assertFalse(Files.exists(original.toPath()));

            if ( Files.exists(temp.toPath()) )
            {
                FileUtils.moveFile(
                        temp,
                        original
                );
            }
        }
        catch ( Exception exception )
        {
            fail();
        }
    }
}