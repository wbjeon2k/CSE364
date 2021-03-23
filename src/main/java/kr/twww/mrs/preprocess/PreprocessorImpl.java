package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.*;

import java.util.ArrayList;

public class PreprocessorImpl extends PreprocessorBase implements Preprocessor, DataReader
{
    DataReader dataReader;

    public PreprocessorImpl()
    {
        dataReader = new DataReaderImpl();
    }

    @Override
    public ArrayList<Score> GetScoreList( String _category, String _occupation )
    {
        var genreList = GetGenreList(_category);
        var occupation = GetOccupation(_occupation);

        var result = GetScoreList(
                genreList,
                occupation,
                GetUserList(),
                GetMovieList(),
                GetRatingList()
        );

        return result;
    }

    @Override
    public ArrayList<User> GetUserList()
    {
        return dataReader.GetUserList();
    }

    @Override
    public ArrayList<Movie> GetMovieList()
    {
        return dataReader.GetMovieList();
    }

    @Override
    public ArrayList<Rating> GetRatingList()
    {
        return dataReader.GetRatingList();
    }

    @Override
    public ArrayList<Movie.Genre> GetGenreList( String genreText )
    {
        // *****질문***** 굳이 주어진 텍스트를 이용안하고 enum Genre 리스트로 왜 반환하는지 모르겠습니다.
        // movies.dat 파일 불러오기
        // movies.dat 파일을 '::' 기준으로 나누어 List[3] 크기 리스트에 담기
        // 리스트 3번째 요소가 Genres 이므로 List[2] == genreText 와 비교
        // 둘이 같다면, MovieID인 List[0]를 새로운 리스트에 어펜드 TargetMovie.append(List[0])
        // return TargerMovie
        // TODO: 주어진 텍스트를 enum Genre 리스트로 반환
        return null;
    }

    @Override
    public User.Occupation GetOccupation( String occupationText )
    {
        // users.dat 파일 불러오기
        // users.dat 파일을 '::' 기준으로 나누고, 4번째 요소인 Occupation 을 enum Occupation으로 반환 후 List[5] 크기 리스트에 담기
        // 리스트 4번째 요소가 Occupation 이므로 List[3] == occupationText 와 비교
        // 둘이 같다면, UserID인 List[0]를 새로운 리스트에 어펜드 TargetUser.append(List[0])
        // return TargetUser
        // TODO: 주어진 텍스트를 enum Occupation으로 반환
        // ****질문**** 아예 occupationText 를 숫자로 반환하는게 더 효율적이지 않나요?
        return null;
    }

    @Override
    public ArrayList<Score> GetScoreList(
            ArrayList<Movie.Genre> genreList,
            User.Occupation occupation,
            ArrayList<User> userList,
            ArrayList<Movie> movieList,
            ArrayList<Rating> ratingList
    )
    {
        // ratings.dat 파일을 '::' 기준으로 나누면 4개의 요소이므로 list[4] 에 담는다.
        // ratings.dat 파일에서 UserID 를 뜻하는 list[0] 과 occupation 비교
        // ratings.dat 파일에서 MovieID 를 뜻하는 list[1] 과 genreList 비교
        // 이때 세번째 요소 list[2] 는 Rating이다
        // if list[0] == occupation[i] and list[1] == genreList: TargetScore.append(list[2])
        /**
         * TODO:
         * 1. 카테고리(장르)에 해당하는 모든 영화 필터링
         * 2. 해당하는 영화 및 동일한 직업의 유저들의 평가 필터링
         * 3. 영화마다 새로운 Score에 설정 및 해당하는 모든 평가 추가
         * 4. Score 리스트를 반환
         *
         * 참고:
         * +. User.ConvertOccupation()
         * +. Movie.ConvertGenre()
         */
        return null;
    }
}
