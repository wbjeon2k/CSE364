package kr.twww.mrs.preprocess;

import kr.twww.mrs.preprocess.object.Score;

import java.util.ArrayList;

public interface Preprocessor
{
    /**
     * 같은 속성을 가진 유저들의
     * 데이터로 계산한 추천 영화 리스트를 반환한다.
     * @param _gender 유저의 성별
     * @param _age 유저의 나이
     * @param _occupation 유저의 직업
     * @return 추천하는 영화 상위 10개 리스트
     */
    ArrayList<Score> GetRecommendList(
            String _gender,
            String _age,
            String _occupation
    ) throws Exception;

    /**
     * 카테고리에 해당하는 영화들을 동일한 속성을 가진 유저들의
     * 데이터로 계산한 추천 영화 리스트를 반환한다.
     * @param _gender 유저의 성별
     * @param _age 유저의 나이
     * @param _occupation 유저의 직업
     * @param _categories 영화 카테고리(장르)
     * @return 추천하는 영화 상위 10개 리스트
     */
    ArrayList<Score> GetRecommendList(
            String _gender,
            String _age,
            String _occupation,
            String _categories
    ) throws Exception;

    /**
     * 영화 제목에 해당하는 영화로 추천한 영화 리스트를
     * 지정한 수 만큼 반환한다.
     * @param _title 영화 제목
     * @param _limit 추천 수 ("" = 10)
     * @return 추천하는 영화 상위 10개 리스트
     */
    ArrayList<Score> GetRecommendList(
            String _title,
            String _limit
    ) throws Exception;

    //init routine
    //public void PreprocessorImplInit() throws Exception;
}
