package kr.twww.mrs.data.object;

import java.io.Serializable;

public class Rating implements Serializable
{
    public int userId;
    public int movieId;
    public int rating;
    public int timestamp;
}
