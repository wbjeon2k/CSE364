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
    {
        // TODO: ratings의 모든 점수 합계를 ratings의 크기로 나눈 값 반환

        return 0f;
    }
}
