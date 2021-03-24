package kr.twww.mrs.data;

import java.util.ArrayList;

public abstract class DataReaderBase
{
    public abstract String GetPathFromDataType( DataType dataType );
    public abstract ArrayList<String[]> ReadTextFromFile(String path );

    public abstract ArrayList<String[]> ToUserList(String text );
    public abstract ArrayList<String[]> ToMovieList(String text );
    public abstract ArrayList<String[]> ToRatingList(String text );
}
