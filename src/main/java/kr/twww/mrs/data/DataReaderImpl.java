package kr.twww.mrs.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
            return br.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        출처: https://yeolco.tistory.com/106 [열코의 프로그래밍 일기]
        // TODO: 주어진 경로의 파일을 읽어 String으로 반환
        return null;
    }

    @Override
    public ArrayList<User> ToUserList( String text )
    {
        // TODO: 주어진 텍스트를 User 리스트로 반환
        return null;
    }

    @Override
    public ArrayList<Movie> ToMovieList( String text )
    {
        // TODO: 주어진 텍스트를 Movie 리스트로 반환
        return null;
    }

    @Override
    public ArrayList<Rating> ToRatingList( String text )
    {
        // TODO: 주어진 텍스트를 Rating 리스트로 반환
        return null;
    }
}
