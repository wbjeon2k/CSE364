package kr.twww.mrs.data;

import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.User;
import kr.twww.mrs.data.repository.MovieRepository;
import kr.twww.mrs.data.repository.PosterRepository;
import org.apache.spark.mllib.recommendation.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static kr.twww.mrs.data.DataType.*;

@SuppressWarnings("FieldCanBeLocal")
@Service
public class DataReaderImpl extends DataReaderBase implements DataReader
{
    private final String PATH_DATA = "./data/";
    private final String SUFFIX = "s.dat";
    private final String SUFFIX_CSV = "s.csv";

    private boolean movieRepoInit = false;
    private boolean posterRepoInit = false;

    @Autowired
    public MovieRepository movieRepository;

    @Autowired
    public PosterRepository posterRepository;


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

    public void ReadPosterCsv(){

    }

    @Override
    public String GetPosterLink(int id) throws Exception {
        if(posterRepoInit == false){

        }
        return null;
    }

    @Override
    public ArrayList<User> GetUserList() throws Exception
    {
        var path = GetPathFromDataType(USER);
        var text = ReadTextFromFile(path);

        return ToUserList(text);
    }

    @Override
    public ArrayList<Movie> GetMovieList() throws Exception
    {
        if(movieRepoInit == false){

            var path = GetPathFromDataType(MOVIE);
            var text = ReadTextFromFile(path);

            movieRepoInit = true;
            return ToMovieList(text);
        }
        return (ArrayList<Movie>) movieRepository.findAll();
    }

    @Override
    public ArrayList<Rating> GetRatingList() throws Exception
    {
        var path = GetPathFromDataType(RATING);
        var text = ReadTextFromFile(path);

        return ToRatingList(text);
    }

    @Override
    public ArrayList<Link> GetLinkList() throws Exception
    {
        var path = GetPathFromDataType(LINK);
        var text = ReadTextFromFile(path);

        return ToLinkList(text);
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

                movieRepository.save(newMovie);
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
