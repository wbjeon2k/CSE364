package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.Movie;
import kr.twww.mrs.data.Rating;

import java.util.ArrayList;

public class Score
{
    private Movie movie;
    private ArrayList<Rating> ratings;

    public void SetMovie( Movie _movie )
    {
        movie = _movie;
        ratings = new ArrayList<>();
    }

    public void AddRating( Rating _rating )
    {
        ratings.add(_rating);
    }

    public Movie GetMovie()
    {
        return movie;
    }

    public float GetScore()
    {   // 인풋 데이터와 직업와 장르 같은 영화의 평점은 TargetScore에 들어있다.
        // return TargetScore.mean()
        // TODO: ratings의 모든 점수 합계를 ratings의 크기로 나눈 값 반환
        return 0f;
    }
}
