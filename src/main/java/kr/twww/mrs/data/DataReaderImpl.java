package kr.twww.mrs.data;

import org.apache.maven.shared.utils.StringUtils;

import java.io.*;
import java.util.ArrayList;

import static kr.twww.mrs.data.DataType.*;

public class DataReaderImpl extends DataReaderBase implements DataReader
{
    @Override
    public ArrayList<User> GetUserList()
    {
        var path = GetPathFromDataType(USER);
        var text = ReadTextFromFile(path);
        var result = ToUserList(text);

        return result;
    }

    @Override
    public ArrayList<Movie> GetMovieList()
    {
        var path = GetPathFromDataType(MOVIE);
        var text = ReadTextFromFile(path);
        var result = ToMovieList(text);

        return result;
    }

    @Override
    public ArrayList<Rating> GetRatingList()
    {
        var path = GetPathFromDataType(RATING);
        var text = ReadTextFromFile(path);
        var result = ToRatingList(text);

        return result;
    }

    @Override
    public String GetPathFromDataType( DataType dataType )
    {
        // 데이터 타입 별 경로 설정
        if (dataType == USER)
            return "./data/users.dat";
        else if (dataType == MOVIE)
            return "./data/movies.dat";
        else if  (dataType == RATING)
            return "./data/ratings.dat";
        else
            return null;
    }

    @Override
    public String ReadTextFromFile( String path )
    {
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int)file.length()];
            fis.read(data);
            fis.close();

            String str = new String(data);
            return str;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO: 주어진 경로의 파일을 읽어 String으로 반환
        return null;
    }

    @Override
    public ArrayList<User> ToUserList( String text )
    {
        var resultUserList = new ArrayList<User>();

        String str = text;
        InputStream is = new ByteArrayInputStream(str.getBytes());
        // BufferedReader를 이용해 한 줄씩 읽기
        try {
            BufferedReader brUser = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = brUser.readLine()) != null) {

                var MyUser = new User();
                //한 줄씩 받으면서 MyUser 객체에 변수를 넣는다.
                String[] strUser = line.split("::");
                MyUser.userId = Integer.parseInt(strUser[0]);
                MyUser.gender = MyUser.ConvertGender(strUser[1].charAt(0));
                MyUser.age = MyUser.ConvertAge(Integer.parseInt(strUser[2]));
                MyUser.occupation = MyUser.ConvertOccupation(Integer.parseInt(strUser[3]));
                MyUser.zipCode = MyUser.zipCode.valueOf(strUser[4]);
                //객체를 리스트에 add
                resultUserList.add(MyUser);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultUserList;
    }

    @Override
    public ArrayList<Movie> ToMovieList( String text )
    {
        var resultMovieList = new ArrayList<Movie>();

        String str = text;

        InputStream is = new ByteArrayInputStream(str.getBytes());
        // BufferedReader를 이용해 한 줄씩 읽기
        try {
            BufferedReader brMovie = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = brMovie.readLine()) != null) {

                var MyMovie = new Movie();
                //한 줄씩 받으면서 MyMovie 객체에 변수를 넣는다.
                String[] strMovie = line.split("::");
                MyMovie.movieId = Integer.parseInt(strMovie[0]);
                MyMovie.title = strMovie[1];
                //MyMovie 객체의 genres 변수가 ArrayList<Genre> 이다
                //따라서 데이터를 "|" 기준 파싱하고 각각을 Enum Genre로 변환 후
                //ArrayList<Genre>에 add 함
                var resultGenreList = new ArrayList<Movie.Genre>();
                String[] strGenre = strMovie[2].split("\\|");
                for(String i : strGenre){

                    var convertedGenre = Movie.ConvertGenre(i);

                    // 잘못된 장르
                    if ( convertedGenre == null ) return null;

                    resultGenreList.add(convertedGenre);
                }
                MyMovie.genres = resultGenreList;
                //객체를 리스트에 add
                resultMovieList.add(MyMovie);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMovieList;

    }

    @Override
    public ArrayList<Rating> ToRatingList( String text )
    {
        var resultRatingList = new ArrayList<Rating>();

        if ( text == null ) return null;

        if ( text.isEmpty() ) return new ArrayList<>();

        var splitRating = text.split("\\r?\\n");

        for ( var i : splitRating )
        {
            var splitData = i.split("::");

            var newRating = new Rating();
            newRating.userId = Integer.parseInt(splitData[0]);
            newRating.movieId = Integer.parseInt(splitData[1]);
            newRating.rating = Integer.parseInt(splitData[2]);
            newRating.timestamp = Integer.parseInt(splitData[3]);

            resultRatingList.add(newRating);
        }

        return resultRatingList;

    }
}
