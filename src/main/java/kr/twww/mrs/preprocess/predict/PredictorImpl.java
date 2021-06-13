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
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class PredictorImpl extends PredictorBase implements Predictor, InitializingBean, DisposableBean
{
//    private final String PATH_DATA = "./data/";
    private final String PATH_DATA = "/data/";
    private final String PATH_DATA_CHECKSUM = PATH_DATA + "checksum";
    private final String PATH_DATA_MODEL = PATH_DATA + "model";

    @Autowired
    private DataReader dataReader;

    private JavaSparkContext javaSparkContext;
    private MatrixFactorizationModel model;

    @Override
    public boolean LoadModel() throws Exception
    {
        if ( model != null ) return true;

        var savedChecksum = GetSavedChecksum();

        if ( savedChecksum == null ) return false;
        if ( !savedChecksum.equals(GetChecksum()) ) return false;

        var modelPath = Paths.get(PATH_DATA_MODEL);
        var modelHadoopPath = "file:///" + Paths.get(PATH_DATA_MODEL).toAbsolutePath();

        if ( Files.isDirectory(modelPath) )
        {
            System.out.println("Info: Loading model ...");

            model = MatrixFactorizationModel.load(javaSparkContext.sc(), modelHadoopPath);
        }

        return (model != null);
    }

    @Override
    public boolean CreateModel( ArrayList<Rating> ratingList ) throws Exception
    {
        try{
            var modelHadoopPath = "file:///" + Paths.get(PATH_DATA_MODEL).toAbsolutePath();

            var ratingRDD = javaSparkContext
                    .parallelize(ratingList);

            System.out.println("Info: Creating model ...");

            model = ALS.train(JavaRDD.toRDD(ratingRDD), 15, 11, 0.01);

            System.out.println("Info: Finish training ...");

            if ( model == null ){
                //System.out.println("Info: Creating model failed ...");
                return false;
            }

            System.out.println("Info: Deletemodel() in Creating model ...");
            DeleteModel();

            System.out.println("Info: Creating model model.save start ...");
            model.save(javaSparkContext.sc(), modelHadoopPath);

            System.out.println("Info: Creating model savechecksum start ...");
            SaveChecksum(GetChecksum());

            System.out.println("Info: Creating model end ...");
            return true;
        }
        catch (Exception e){
            throw new Exception("Error in CreateModel : " + e.getMessage());
        }
    }

    private void DeleteModel() throws Exception
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
            throw new Exception("Delete model failed");
        }
    }

    @Override
    public List<Rating> GetPredictList(
            List<User> filteredUserList,
            List<Movie> filteredMovieList
    ) throws Exception
    {
        if ( model == null )
        {
            throw new Exception("Model was not loaded or created");
        }

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

        System.out.println("Info: Predicting ...");

        return model.predict(pairRDD).collect();
    }

    @Override
    public void Close()
    {
        javaSparkContext.close();
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

        javaSparkContext = new JavaSparkContext(
                "local",
                "predict"
        );

        javaSparkContext.setLogLevel("OFF");

        model = null;
    }

    @Override
    public String GetChecksum() throws Exception
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
                throw new Exception("Get checksum failed");
            }
        }

        return DigestUtils.sha256Hex(checksum.toString());
    }

    @Override
    public String GetSavedChecksum() throws Exception
    {
        if ( !Files.exists(Paths.get(PATH_DATA_CHECKSUM)) ) return null;

        return dataReader.ReadTextFromFile(PATH_DATA_CHECKSUM);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void SaveChecksum( String checksum ) throws Exception
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
            throw new Exception("Save checksum failed");
        }
    }

    @Override
    public void afterPropertiesSet()
    {
        Setup();
    }

    @Override
    public void destroy()
    {
        Close();
    }
}
