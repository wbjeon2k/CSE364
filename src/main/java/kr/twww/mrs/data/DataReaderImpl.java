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
        // TODO: 데이터 타입에 따른 데이터파일 경로 반환
    }

    @Override
    public String ReadTextFromFile( String path )
    {
        try {
//            BufferedReader br = new BufferedReader(new FileReader(path));
//            // 데이터 전체 다 받도록 코드 수정함
//            String line = br.readLine();
//            String resultRead = "";
//            while (line != null){
//                resultRead = resultRead + (line + '\n');
//                line = br.readLine();
//            }
//            return StringUtils.chop(resultRead);

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
        // convert String into InputStream
        InputStream is = new ByteArrayInputStream(str.getBytes());
        // read it with BufferedReader
        try {
            BufferedReader brUser = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = brUser.readLine()) != null) {
                // MyUser 선언을 반복문 안으로 오도록 수정했습니다.
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
        // TODO: 주어진 텍스트를 User 리스트로 반환
        return resultUserList;
    }

    @Override
    public ArrayList<Movie> ToMovieList( String text )
    {
        var resultMovieList = new ArrayList<Movie>();

        String str = text;
        // convert String into InputStream
        InputStream is = new ByteArrayInputStream(str.getBytes());
        // read it with BufferedReader
        try {
            BufferedReader brMovie = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = brMovie.readLine()) != null) {
                //MyMovie를 반복문 안에서 선언하도록 수정했습니다.
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
                    //사실 convert 함수 써야되는 어떻게 하는지 모르겠다.
//                    resultGenreList.add(Movie.ConvertGenre(i));
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
        // TODO: 주어진 텍스트를 Movie 리스트로 반환
    }

    @Override
    public ArrayList<Rating> ToRatingList( String text )
    {
        var resultRatingList = new ArrayList<Rating>();

//        String str = text;
//        // convert String into InputStream
//        InputStream is = new ByteArrayInputStream(str.getBytes());
//        // read it with BufferedReader
//        try {
//            BufferedReader brUser = new BufferedReader(new InputStreamReader(is));
//            String line;
//            while ((line = brUser.readLine()) != null) {
//                // MyRating 을 반복문 안에서 선언하도록 수정했습니다.
//                var MyRating = new Rating();
//                //한 줄씩 받으면서 MyRating 객체에 변수를 넣는다.
//                String[] strRating = line.split("::");
//                MyRating.userId = Integer.parseInt(strRating[0]);
//                MyRating.movieId = Integer.parseInt(strRating[1]);
//                MyRating.rating = Integer.parseInt(strRating[2]);
//                MyRating.timestamp = Integer.parseInt(strRating[3]);
//
//                resultRatingList.add(MyRating);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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
        // TODO: 주어진 텍스트를 Rating 리스트로 반환
    }
}
