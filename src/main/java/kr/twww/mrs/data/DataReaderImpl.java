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
    public ArrayList<String[]> ReadTextFromFile(String path ) {
        ArrayList<String[]> MyList = new ArrayList<>()
        try {

            BufferedReader br = new BufferedReader(new FileReader(path));

            String line = null;

            while((line = br.readLine()) != null) {
                String str = line;
                String[] ElementList = str.split("::");
                MyList.add(ElementList);

            }

            br.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {


            e.printStackTrace();

        }
        //2차원 배열 출력
        return (ArrayList<String[]>) MyList;


        // TODO: 주어진 경로의 파일을 읽어 String으로 반환
    }

    @Override
    public ArrayList<User> ToUserList( String text )
    {
        for(String i : text ){
            //i 는 users.dat 파일 한 줄씩임
            //i의 순서는 userid, gender, age, occupation, zip-code 임
            ConvertGender(i[1]);
            ConvertAge(i[2]);
            ConvertOccupation(i[3]);
        }
        // TODO: 주어진 텍스트를 User 리스트로 반환
        return null;
    }

    @Override
    public ArrayList<Movie> ToMovieList( String text )
    {
        for(String i : text ){
            //i 는 movies.dat 파일 한 줄씩임
            //i 의 세번째 값은 Genres 임
            ConvertGenre(i[2]);
        }

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
