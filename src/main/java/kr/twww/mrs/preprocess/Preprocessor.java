package kr.twww.mrs.preprocess;

import kr.twww.mrs.preprocess.object.Score;

import java.util.ArrayList;

public interface Preprocessor
{
    /**
     * 같은 속성을 가진 유저들의
     * 데이터로 계산한 추천 영화 리스트({@link ArrayList})를 반환한다.
     * @param _gender 유저의 성별
     * @param _age 유저의 나이
     * @param _occupation 유저의 직업
     * @return 추천하는 영화 상위 10개 리스트
     */
    ArrayList<Score> GetRecommendList( String _gender, String _age, String _occupation );

    /**
     * 카테고리에 해당하는 영화들을 동일한 속성을 가진 유저들의
     * 데이터로 계산한 추천 영화 리스트({@link ArrayList})를 반환한다.
     * @param _gender 유저의 성별
     * @param _age 유저의 나이
     * @param _occupation 유저의 직업
     * @param _categories 영화 카테고리(장르)
     * @return 추천하는 영화 상위 10개 리스트
     */
    ArrayList<Score> GetRecommendList( String _gender, String _age, String _occupation, String _categories );
}
