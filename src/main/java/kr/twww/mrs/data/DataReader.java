package kr.twww.mrs.data;

import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.User;
import org.apache.spark.mllib.recommendation.Rating;

import java.util.ArrayList;

public interface DataReader
{
    /**
     * 해당하는 데이터 타입{@link DataType}의 데이터 파일의 경로를 반환한다.
     * @param dataType 데이터 타입
     * @return 데이터 파일 경로
     */
    String GetPathFromDataType( DataType dataType );

    /**
     * 해당하는 경로의 파일({@link String})의 텍스트를 반환한다.
     * @param path 파일 경로
     * @return 파일 텍스트 내용
     */
    String ReadTextFromFile( String path );

    /**
     * 데이터셋에서 읽어낸 유저({@link User}) 데이터를
     * 리스트({@link ArrayList})로 반환한다.
     * @return 유저 데이터 리스트
     */
    ArrayList<User> GetUserList();

    /**
     * 데이터셋에서 읽어낸 영화({@link Movie}) 데이터를
     * 리스트({@link ArrayList})로 반환한다.
     * @return 영화 데이터 리스트
     */
    ArrayList<Movie> GetMovieList();

    /**
     * 데이터셋에서 읽어낸 평가({@link Rating}) 데이터를
     * 리스트({@link ArrayList})로 반환한다.
     * @return 평가 데이터 리스트
     */
    ArrayList<Rating> GetRatingList();

    /**
     * 데이터셋에서 읽어낸 링크({@link Link}) 데이터를
     * 리스트({@link ArrayList})로 반환한다.
     * @return 링크 데이터 리스트
     */
    ArrayList<Link> GetLinkList();
}
