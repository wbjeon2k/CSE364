package kr.twww.mrs.preprocess;

import java.util.ArrayList;

public interface Preprocessor
{
    /**
     * 카테고리에 해당하는 영화들을 동일한 직업을 가진 유저들의
     * 평점({@link Score})으로 계산한 리스트({@link ArrayList})를 반환한다.
     * @param category 영화 카테고리
     * @param occupation 유저의 직업
     * @return 해당하는 영화들의 평점 리스트
     */
    public ArrayList<Score> GetScores( String category, String occupation );
}
