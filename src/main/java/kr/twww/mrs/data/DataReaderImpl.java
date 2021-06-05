package kr.twww.mrs.data;

import com.mongodb.DBCursor;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.Poster;
import kr.twww.mrs.data.object.User;
import kr.twww.mrs.data.repository.*;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.apache.spark.mllib.recommendation.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Array;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

import static kr.twww.mrs.data.DataType.*;

@SuppressWarnings("FieldCanBeLocal")
@Service
public class DataReaderImpl extends DataReaderBase implements DataReader
{
    private final String PATH_DATA = "./data/";
    private final String SUFFIX = "s.dat";
    private final String SUFFIX_CSV = ".csv";

    private static ArrayList<Rating> ratingListAsRepo;

    static boolean movieRepoInit = false;
    static boolean posterRepoInit = false;
    static boolean ratingRepoInit = false;
    static boolean userRepoInit = false;
    static boolean linkRepoInit = false;

    @Autowired
    public static MovieRepository movieRepository;

    @Autowired
    public static PosterRepository posterRepository;

    @Autowired
    public static RatingRepository ratingRepository;

    @Autowired
    public static UserRepository userRepository;

    @Autowired
    public static LinkRepository linkRepository;

    public void DataReaderImplInit() throws Exception {
        System.out.println("Initializing DataReader");
        InitPosterRepo();
        InitMovieRepo();
        InitLinkRepo();
        InitUserRepo();
        System.out.println("Initializing complete");
    }

    public void InitPosterRepo() throws Exception{
        try{
            readCsvToPoster();
            posterRepoInit = true;
        }
        catch (Exception e){
            throw new Exception("Error in : InitPosterRepo");
        }
    }

    public void InitUserRepo() throws Exception {
        System.out.println("InitUserRepo start");
        try{
            var path = GetPathFromDataType(USER);
            var text = ReadTextFromFile(path);

            var result =  ToUserList(text);
            for(User u : result){
                userRepository.insert(u);
            }
            userRepoInit = true;
        }
        catch (Exception e){
            throw new Exception("Error in : InitUserRepo");
        }
    }

    public void InitMovieRepo() throws Exception {
        try{
            //movieRepository.deleteAll();
            var path = GetPathFromDataType(MOVIE);
            var text = ReadTextFromFile(path);
            var result =ToMovieList(text);
            for(int i=0;i< result.size();++i){
                movieRepository.save(result.get(i));
            }
            movieRepoInit = true;
        }
        catch (Exception e){
            throw new Exception("Error in : InitMovieRepo");
        }
    }

    public void InitLinkRepo() throws Exception{
        try{
            //linkRepository.deleteAll();
            var path = GetPathFromDataType(LINK);
            var text = ReadTextFromFile(path);

            var result =  ToLinkList(text);
            for(int i=0;i< result.size();++i){
                linkRepository.save(result.get(i));
            }
            linkRepoInit = true;
        }
        catch (Exception e){
            throw new Exception("Error in : InitLinkRepo");
        }
    }

    @Override
    public String GetPathFromDataType( DataType dataType ) throws Exception
    {
        if ( dataType != null )
        {
            // ####s.dat
            return PATH_DATA
                    + dataType.name().toLowerCase()
                    + SUFFIX;
        }

        throw new Exception("Invalid data type");
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public String ReadTextFromFile( String path ) throws Exception
    {
        try
        {
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.ISO_8859_1);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            var data = new char[(int)file.length()];
            bufferedReader.read(data);

            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();

            return new String(data);
        }
        catch ( IOException e )
        {
            throw new Exception("Reading file failed");
        }
    }



    public void readCsvToPoster() throws Exception {
        var readChk = new int[10000];
        Arrays.fill(readChk, 0);

        try{
            String filePath = PATH_DATA + "movie_poster" + SUFFIX_CSV;
            CSVReader reader = new CSVReader(new FileReader(filePath)); // 1
            CsvToBean<Poster> CTB = new CsvToBeanBuilder(reader)
                    .withType(Poster.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            Iterator<Poster> csvIterator = CTB.iterator();

            while(csvIterator.hasNext()){
                Poster poster = csvIterator.next();

                assert(poster.movID >= 0);
                if(readChk[poster.movID] == 1) continue;
                else readChk[poster.movID] = 1;

                posterRepository.save(poster);
            }
        }
        catch (Exception e){
            throw new Exception("Error in readCsvToPoster: "+ e.getMessage());
        }
    }

    @Override
    public Poster GetPoster(int movID) throws Exception {
        try{

            if(posterRepoInit == false){
                var initrepo = posterRepository.findAll();
                if(initrepo.size() > 0){
                    posterRepository.deleteAll();
                }
                readCsvToPoster();
                posterRepoInit = true;
            }

            var posterList = posterRepository.findAll();
            for(var p : posterList){
                if(p.getMovID() == movID){
                    return p;
                }
            }

            //if there is no match
            var tmpPoster = new Poster();
            tmpPoster.movID = movID;
            tmpPoster.posterLink = "";
            return tmpPoster;
        }
        catch (Exception e){
            throw new Exception("Error in GetPoster : "+ e.getMessage());
        }

    }

    @Override
    public ArrayList<User> GetUserList() throws Exception
    {
        System.out.println("GetUserList start.");
        try{
            if(userRepoInit == false){
                System.out.println("userrepoinit start.");
                InitUserRepo();
                userRepoInit = true;
            }

            System.out.println("try to access userRepository.");
            return (ArrayList<User>) userRepository.findAll();
        }
        catch (Exception e){
            throw new Exception("Error in GetUserList : " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Movie> GetMovieList() throws Exception
    {
        try{
            if(movieRepoInit == false){
                InitMovieRepo();
                movieRepoInit = true;
            }
            return (ArrayList<Movie>) movieRepository.findAll();
        }
        catch (Exception e) {
            throw new Exception("Error in GetMovieList: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Rating> GetRatingList() throws Exception
    {
        /*
        if(ratingRepoInit == false){
            var path = GetPathFromDataType(RATING);
            var text = ReadTextFromFile(path);

            var result = ToRatingList(text);
            for(int i=0;i< result.size();++i){
                ratingRepository.save(result.get(i));
            }
            ratingRepoInit = true;
            return result;
        }
        return (ArrayList<Rating>) ratingRepository.findAll();
         */

        //mongodb query 1M calls are too slow. direct file access is faster.
        if(ratingListAsRepo.isEmpty()){
            var path = GetPathFromDataType(RATING);
            var text = ReadTextFromFile(path);

            ratingListAsRepo = ToRatingList(text);
        }
        return ratingListAsRepo;
    }

    @Override
    public ArrayList<Link> GetLinkList() throws Exception
    {
        if(linkRepoInit == false){
            InitLinkRepo();
            linkRepoInit = true;
        }
        return (ArrayList<Link>) linkRepository.findAll();
    }


    @Override
    public ArrayList<User> ToUserList( String text ) throws Exception
    {
        if ( text.isEmpty() )
        {
            throw new Exception("Empty user data file");
        }

        var result = new ArrayList<User>();

        var splitText = text.split("\\r?\\n");

        try
        {
            for ( var i : splitText )
            {
                var splitData = i.split("::");

                var newUser = new User();
                newUser.userId = Integer.parseInt(splitData[0]);
                newUser.gender = User.ConvertGender(splitData[1]);
                newUser.age = User.ConvertAge(splitData[2]);
                newUser.occupation = User.ConvertOccupationByIndex(Integer.parseInt(splitData[3]));
                newUser.zipCode = splitData[4];

                result.add(newUser);
            }
        }
        catch ( Exception exception )
        {
            throw new Exception("Invalid user data");
        }

        return result;
    }

    @Override
    public ArrayList<Movie> ToMovieList( String text ) throws Exception
    {
        if ( text.isEmpty() )
        {
            throw new Exception("Empty movie data file");
        }

        var result = new ArrayList<Movie>();

        var splitText = text.split("\\r?\\n");

        try
        {
            for ( var i : splitText )
            {
                var splitData = i.split("::");

                var newMovie = new Movie();
                newMovie.movieId = Integer.parseInt(splitData[0]);
                newMovie.title = splitData[1];
                newMovie.genres = GetGenreList(splitData[2]);

                result.add(newMovie);
            }
        }
        catch ( Exception exception )
        {
            throw new Exception("ToMovieList error : Invalid movie data" + exception.getMessage());
        }

        return result;
    }

    private ArrayList<Movie.Genre> GetGenreList( String genresText ) throws Exception
    {
        var genreList = new ArrayList<Movie.Genre>();
        var splitGenre = genresText.split("\\|");

        for ( String j : splitGenre )
        {
            genreList.add(Movie.ConvertGenre(j));
        }

        return genreList;
    }

    @Override
    public ArrayList<Rating> ToRatingList( String text ) throws Exception
    {
        if ( text.isEmpty() )
        {
            throw new Exception("Empty rating data file");
        }

        var result = new ArrayList<Rating>();

        var splitText = text.split("\\r?\\n");

        try
        {
            for ( var i : splitText )
            {
                var splitData = i.split("::");

                var userId = Integer.parseInt(splitData[0]);
                var movieId = Integer.parseInt(splitData[1]);
                var rating = Integer.parseInt(splitData[2]);
//                var timestamp = Integer.parseInt(splitData[3]);

                var newRating = new Rating(
                        userId,
                        movieId,
                        rating
                );

                result.add(newRating);
            }
        }
        catch ( Exception exception )
        {
            throw new Exception("Invalid rating data");
        }

            return result;
    }

    @Override
    public ArrayList<Link> ToLinkList( String text ) throws Exception
    {
        if ( text.isEmpty() )
        {
            throw new Exception("Empty link data file");
        }

        var result = new ArrayList<Link>();

        var splitText = text.split("\\r?\\n");

        try
        {
            for ( var i : splitText )
            {
                var splitData = i.split("::");

                var newRating = new Link();
                newRating.movieId = Integer.parseInt(splitData[0]);
                newRating.imdbId = splitData[1];

                result.add(newRating);
            }
        }
        catch ( Exception exception )
        {
            throw new Exception("Invalid link data");
        }

        return result;
    }
}
