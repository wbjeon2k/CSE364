package kr.twww.mrs.preprocess.predict;

import kr.twww.mrs.data.DataReader;
import kr.twww.mrs.data.DataType;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import scala.Tuple2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PredictorImpl extends PredictorBase implements Predictor
{
    private final String PATH_DATA = "./data/";
    private final String PATH_DATA_CHECKSUM = PATH_DATA + "checksum";
    private final String PATH_DATA_MODEL = PATH_DATA + "model";

    private final DataReader dataReader;

    private final JavaSparkContext javaSparkContext;
    private MatrixFactorizationModel model;

    public PredictorImpl( DataReader _dataReader )
    {
        Setup();

        dataReader = _dataReader;

        javaSparkContext = new JavaSparkContext(
                "local",
                "predict"
        );

        model = null;
    }

    @Override
    public boolean LoadModel()
    {
        var savedChecksum = GetSavedChecksum();

        if ( savedChecksum == null ) return false;
        if ( !savedChecksum.equals(GetChecksum()) ) return false;

        var modelPath = Paths.get(PATH_DATA_MODEL);
        var modelHadoopPath = "file:///" + Paths.get(PATH_DATA_MODEL).toAbsolutePath();

        model = null;

        if ( Files.isDirectory(modelPath) )
        {
            System.out.println("Info: Loading model ... ");

            model = MatrixFactorizationModel.load(javaSparkContext.sc(), modelHadoopPath);
        }

        return (model != null);
    }

    @Override
    public boolean CreateModel( ArrayList<Rating> ratingList )
    {
        var modelHadoopPath = "file:///" + Paths.get(PATH_DATA_MODEL).toAbsolutePath();

        var ratingRDD = javaSparkContext
                .parallelize(ratingList);

        System.out.println("Info: Creating model ... ");

        model = ALS.train(JavaRDD.toRDD(ratingRDD), 10, 20, 0.01);

        if ( model == null ) return false;

        DeleteModel();
        model.save(javaSparkContext.sc(), modelHadoopPath);

        SaveChecksum(GetChecksum());

        return true;
    }

    private boolean DeleteModel()
    {
        var path = Paths.get(PATH_DATA_MODEL);

        try
        {
            if ( Files.exists(path) )
            {
                if ( Files.isDirectory(path) )
                {
                    FileUtils.deleteDirectory(new File(PATH_DATA_MODEL));
                }
                else
                {
                    Files.delete(path);
                }
            }
        }
        catch ( Exception e )
        {
            System.out.println("Error: Delete model failed");
            return false;
        }

        return true;
    }

    @Override
    public List<Rating> GetPredictList(
            List<User> filteredUserList,
            List<Movie> filteredMovieList
    )
    {
        if ( model == null ) return null;

        List<Tuple2<Integer, Integer>> pairList = new ArrayList<>();

        filteredUserList.forEach(
                user -> filteredMovieList.forEach(
                        movie ->  pairList.add(
                                new Tuple2<>(user.userId, movie.movieId)
                        )
                )
        );

        var pairRDD = javaSparkContext
                .parallelizePairs(pairList);

        System.out.println("Info: Predicting ... ");

        return model.predict(pairRDD).collect();
    }

    @Override
    public void Close()
    {
        javaSparkContext.close();

        System.setErr(System.out);
    }

    @Override
    public void Setup()
    {
        var dataPath = Paths
                .get(PATH_DATA)
                .toAbsolutePath();

        System.setProperty(
                "hadoop.home.dir",
                dataPath.toString()
        );

        System.setProperty(
                "org.slf4j.simpleLogger.defaultLogLevel",
                "off"
        );

        System.err.close();
    }

    @Override
    public String GetChecksum()
    {
        var checksum = new StringBuilder();

        for ( var i : DataType.values() )
        {
            try
            {
                var path = dataReader.GetPathFromDataType(i);
                var file = new FileInputStream(path);
                checksum.append(DigestUtils.sha256Hex(file));
            }
            catch ( Exception e )
            {
                System.out.println("Error: Get checksum failed");
                return null;
            }
        }

        return DigestUtils.sha256Hex(checksum.toString());
    }

    @Override
    public String GetSavedChecksum()
    {
        if ( !Files.exists(Paths.get(PATH_DATA_CHECKSUM)) ) return null;

        return dataReader.ReadTextFromFile(PATH_DATA_CHECKSUM);
    }

    @Override
    public void SaveChecksum( String checksum )
    {
        try
        {
            var file = new File(PATH_DATA_CHECKSUM);
            file.createNewFile();

            var fileWrite = new FileWriter(file, false);
            fileWrite.write(checksum);
            fileWrite.close();
        }
        catch ( Exception e )
        {
            System.out.println("Error: Save checksum failed");
        }
    }
}
