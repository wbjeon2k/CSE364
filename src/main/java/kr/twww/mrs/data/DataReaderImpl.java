package kr.twww.mrs.data;

import java.util.ArrayList;

public class DataReaderImpl extends DataReaderBase implements DataReader
{
    @Override
    public ArrayList<User> GetUserList()
    {
        var path = GetPathFromDataType(DataType.USER);
        var text = ReadTextFromFile(path);
        var result = ToUserList(text);

        return result;
    }

    @Override
    public ArrayList<Movie> GetMovieList()
    {
        var path = GetPathFromDataType(DataType.MOVIE);
        var text = ReadTextFromFile(path);
        var result = ToMovieList(text);

        return result;
    }

    @Override
    public ArrayList<Rating> GetRatingList()
    {
        var path = GetPathFromDataType(DataType.RATING);
        var text = ReadTextFromFile(path);
        var result = ToRatingList(text);

        return result;
    }

    @Override
    public String GetPathFromDataType( DataType dataType )
    {
        // TODO: 데이터 타입에 따른 데이터파일 경로 반환
        return null;
    }

    @Override
    public String ReadTextFromFile( String path )
    {
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
        // ratings.dat 파일을 '::' 기준으로 나누면 4개의 요소이므로 list[4] 에 담는다.
        // ratings.dat 파일에서 UserID 를 뜻하는 list[0] 과 occupation 비교
        // ratings.dat 파일에서 MovieID 를 뜻하는 list[1] 과 genreList 비교
        // 이때 세번째 요소 list[2] 는 Rating이다
        // if list[0] == occupation[i] and list[1] == genreList: TargetScore.append(list[2])
        // TODO: 주어진 텍스트를 Rating 리스트로 반환
        return null;
    }
}
