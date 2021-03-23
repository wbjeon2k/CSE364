package kr.twww.mrs.data;

import java.util.ArrayList;

public class Movie
{
    public enum Genre
    {
        Action,
        Adventure,
        Animation,
        Children_s,
        Comedy,
        Crime,
        Documentary,
        Drama,
        Fantasy,
        Film_Noir,
        Horror,
        Musical,
        Mystery,
        Romance,
        Sci_Fi,
        Thriller,
        War,
        Western
    }

    public int movieId;
    public String title;
    public ArrayList<Genre> genres;

    public static Genre ConvertGenre( String _genre )
    {
        // *****질문***** 굳이 주어진 텍스트를 이용안하고 enum Genre 리스트로 왜 반환하는지 모르겠습니다.
        // movies.dat 파일 불러오기
        // movies.dat 파일을 '::' 기준으로 나누어 List[3] 크기 리스트에 담기
        // 리스트 3번째 요소가 Genres 이므로 List[2] == genreText 와 비교
        // 둘이 같다면, MovieID인 List[0]를 새로운 리스트에 어펜드 TargetMovie.append(List[0])
        // return TargerMovie
        // 인풋 데이터와 직업와 장르 같은 영화의 평점은 TargetScore에 들어있다.
        // return TargetScore.mean()
        // TODO: 텍스트로 주어진 장르를 enum Genre로 반환
        return null;
    }
}
