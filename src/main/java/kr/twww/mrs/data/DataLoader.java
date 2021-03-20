package kr.twww.mrs.data;

import java.util.ArrayList;

public interface DataLoader
{
    /**
     * 데이터셋에서 읽어낸 유저({@link User}) 데이터를
     * 리스트({@link ArrayList})로 반환한다.
     * @return 유저 데이터 리스트
     */
    public ArrayList<User> GetUserList();

    public ArrayList<Movie> GetMovieList();

    public ArrayList<Rating> GetRatingList();
}
