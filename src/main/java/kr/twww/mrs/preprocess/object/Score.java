package kr.twww.mrs.preprocess.object;

import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;

public class Score
{
    public Movie movie;
    public Link link;
    public double score;

    public Score()
    {
        score = 0.0;
    }
}
