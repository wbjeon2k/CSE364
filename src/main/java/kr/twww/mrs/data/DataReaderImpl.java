package kr.twww.mrs.data;

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
            return "../../../data/users.dat";
        else if (dataType == MOVIE)
            return "../../../data/movies.dat";
        else if  (dataType == RATING)
            return "../../../data/ratings.dat";
        else
            return "NO DATA";
        // TODO: 데이터 타입에 따른 데이터파일 경로 반환
    }

    @Override
    public String ReadTextFromFile( String path )
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            // 데이터 전체 string 타입으로 반환
            String resultRead = br.readLine();
            return resultRead;

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
        var MyUser = new User();
        String str = text;
        // convert String into InputStream
        InputStream is = new ByteArrayInputStream(str.getBytes());
        // read it with BufferedReader
        try {
            BufferedReader brUser = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = brUser.readLine()) != null) {
                //한 줄씩 받으면서 MyUser 객체에 변수를 넣는다.
                String[] strUser = line.split("::");
                MyUser.userId = Integer.parseInt(strUser[0]);
                MyUser.gender = User.Gender.valueOf(strUser[1]);
                MyUser.age = User.Age.valueOf(strUser[2]);
                MyUser.occupation = User.Occupation.valueOf(strUser[3]);
                MyUser.zipCode = Integer.parseInt(strUser[4]);
                //객체를 리스트에 add
                resultUserList.add(MyUser);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO: 주어진 텍스트를 User 리스트로 반환
        return resultUserList;
    }

    @Override
    public ArrayList<Movie> ToMovieList( String text )
    {
        var resultMovieList = new ArrayList<Movie>();
        var MyMovie = new Movie();
        String str = text;
        // convert String into InputStream
        InputStream is = new ByteArrayInputStream(str.getBytes());
        // read it with BufferedReader
        try {
            BufferedReader brMovie = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = brMovie.readLine()) != null) {
                //한 줄씩 받으면서 MyUser 객체에 변수를 넣는다.
                String[] strMovie = line.split("::");
                MyMovie.movieId = Integer.parseInt(strMovie[0]);
                MyMovie.title = strMovie[1];
                //잠시대기
                var resultGenreList = new ArrayList<Movie.Genre>();
                //_genre를 받고 파싱하고 각각의 요소을 enum으로 변환
                String[] strGenre = strMovie[2].split("|");
                for(String i : strGenre){
                    //사실 convert함수 써야되는 어떻게 하는지 모르겠다.
                    resultGenreList.add(Movie.Genre.valueOf(i));
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
        // TODO: 주어진 텍스트를 Movie 리스트로 반환
    }

    @Override
    public ArrayList<Rating> ToRatingList( String text )
    {
        // TODO: 주어진 텍스트를 Rating 리스트로 반환
        return null;
    }
}
