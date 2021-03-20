package kr.twww.mrs.data;

import java.util.ArrayList;

public class DataLoaderImpl implements DataLoader
{
    @Override
    public ArrayList<User> GetUserList()
    {
        return _GetUserList(GetUserData());
    }

    @Override
    public ArrayList<Movie> GetMovieList()
    {
        return _GetMovieList(GetMovieData());
    }

    @Override
    public ArrayList<Rating> GetRatingList()
    {
        return _GetRatingList(GetRatingData());
    }

    public ArrayList<User> _GetUserList( String text )
    {
        // 리스트로 가공해서 반환
        return null;
    }

    public String GetUserData()
    {
        // 텍스트 파일 읽어서 반환
        return null;
    }

    public ArrayList<Movie> _GetMovieList( String text )
    {
        // 리스트로 가공해서 반환
        return null;
    }

    public String GetMovieData()
    {
        // 텍스트 파일 읽어서 반환
        return null;
    }

    public ArrayList<Rating> _GetRatingList( String text )
    {
        // 리스트로 가공해서 반환
        return null;
    }

    public String GetRatingData()
    {
        // 텍스트 파일 읽어서 반환
        return null;
    }
}
