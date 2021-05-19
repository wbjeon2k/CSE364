package kr.twww.mrs.preprocess.predict;

import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.User;
import org.apache.spark.mllib.recommendation.Rating;

import java.util.ArrayList;
import java.util.List;

public interface Predictor
{
    /**
     * 저장된 모델을 불러온다.
     * @return 성공 여부
     */
    boolean LoadModel() throws Exception;

    /**
     * 모델을 새로 생성하고 저장한다.
     * @param ratingList 학습시킬 평가 리스트
     * @return 성공 여부
     */
    boolean CreateModel( ArrayList<Rating> ratingList ) throws Exception;

    /**
     * 불러오거나 생성한 모델을 사용하여 예측한다.
     * @param filteredUserList 예측할 유저 리스트
     * @param filteredMovieList 예측할 영화 리스트
     * @return 예측한 평가 리스트
     */
    List<Rating> GetPredictList(
            List<User> filteredUserList,
            List<Movie> filteredMovieList
    );

    /**
     * {@link org.apache.spark.api.java.JavaSparkContext}를 종료한다.
     */
    void Close();
}
