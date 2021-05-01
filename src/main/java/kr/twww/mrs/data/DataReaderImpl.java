package kr.twww.mrs.data;

import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.Rating;
import kr.twww.mrs.data.object.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static kr.twww.mrs.data.DataType.*;

public class DataReaderImpl extends DataReaderBase implements DataReader
{
    private final String PATH_DATA = "./data/";
    private final String SUFFIX = "s.dat";

    @Override
    public String GetPathFromDataType( DataType dataType )
    {
        // 데이터 타입 별 경로 설정
        if ( dataType != null )
        {
            // ####s.dat
            return PATH_DATA + dataType.name().toLowerCase() + SUFFIX;
        }

            return null;
    }

    @Override
    public ArrayList<User> GetUserList()
    {
        var path = GetPathFromDataType(USER);
        var text = ReadTextFromFile(path);

        return ToUserList(text);
    }

    @Override
    public ArrayList<Movie> GetMovieList()
    {
        var path = GetPathFromDataType(MOVIE);
        var text = ReadTextFromFile(path);

        return ToMovieList(text);
    }

    @Override
    public ArrayList<Rating> GetRatingList()
    {
        var path = GetPathFromDataType(RATING);
        var text = ReadTextFromFile(path);

        return ToRatingList(text);
    }

    @Override
    public ArrayList<Link> GetLinkList()
    {
        var path = GetPathFromDataType(LINK);
        var text = ReadTextFromFile(path);

        return ToLinkList(text);
    }

    @Override
    public String ReadTextFromFile( String path )
    {
        if ( path == null ) return null;
        if ( path.isEmpty() ) return null;

        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int)file.length()];
            fis.read(data);
            fis.close();

            return new String(data);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<User> ToUserList( String text )
    {
        if ( text == null ) return null;
        if ( text.isEmpty() ) return new ArrayList<>();

        var resultUserList = new ArrayList<User>();

        var splitText = text.split("\\r?\\n");

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

        return result;
    }

    @Override
    public ArrayList<Movie> ToMovieList( String text )
    {
        if ( text == null ) return null;
        if ( text.isEmpty() ) return new ArrayList<>();

        var result = new ArrayList<Movie>();

        var splitText = text.split("\\r?\\n");

        for ( var i : splitText )
        {
            var splitData = i.split("::");

            var newMovie = new Movie();
            newMovie.movieId = Integer.parseInt(splitData[0]);
            newMovie.title = splitData[1];

            ArrayList<Movie.Genre> genreList = GetGenreList(splitData[2]);

            if ( genreList == null ) return null;

            newMovie.genres = genreList;

            result.add(newMovie);
        }

        return result;
    }
    private ArrayList<Movie.Genre> GetGenreList( String genresText )
    {
        var genreList = new ArrayList<Movie.Genre>();
        var splitGenre = genresText.split("\\|");

        for( String j : splitGenre )
        {
            var genre = Movie.ConvertGenre(j);

            if ( genre == null ) return null;

            genreList.add(genre);
        }

        return genreList;
    }

    @Override
    public ArrayList<Rating> ToRatingList( String text )
    {
        if ( text == null ) return null;
        if ( text.isEmpty() ) return new ArrayList<>();

        var result = new ArrayList<Rating>();

        var splitText = text.split("\\r?\\n");

        for ( var i : splitText )
        {
            var splitData = i.split("::");

            var newRating = new Rating();
            newRating.userId = Integer.parseInt(splitData[0]);
            newRating.movieId = Integer.parseInt(splitData[1]);
            newRating.rating = Integer.parseInt(splitData[2]);
            newRating.timestamp = Integer.parseInt(splitData[3]);

            result.add(newRating);
        }

        return result;

    }

    @Override
    public ArrayList<Link> ToLinkList( String text )
    {
        if ( text == null ) return null;
        if ( text.isEmpty() ) return new ArrayList<>();

        var result = new ArrayList<Link>();

        var splitText = text.split("\\r?\\n");

        for ( var i : splitText )
        {
            var splitData = i.split("::");

            var newRating = new Link();
            newRating.movieId = Integer.parseInt(splitData[0]);
            newRating.imdbId = splitData[1];

            result.add(newRating);
        }

        return result;
    }
}
