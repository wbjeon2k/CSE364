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
        // TODO: 주어진 텍스트를 enum Genre 리스트로 반환



        return null;
    }

    @Override
    public User.Occupation GetOccupation( String occupationText )
    {
        // TODO: 주어진 텍스트를 enum Occupation으로 반환

        String s = occupationText;
        s = s.replaceAll("\\p{Z}","");
        s = s.replaceAll("\\p{Punct}","");
        s = s.toUpperCase();

        if(!(s.equals("OTHER") || s.equals("ACADEMIC") || s.equals("EDUCATOR") || s.equals("ARTIST") || s.equals("CLERICAL")
                || s.equals("GRADSTUDENT") || s.equals("COLLEGE") || s.equals("ADMIN") || s.equals("CUSTOMERSERVICE")
                || s.equals("DOCTOR") || s.equals("HEALTHCARE") || s.equals("EXECUTIVE") || s.equals("MANAGERIAL")
                || s.equals("FARMER") || s.equals("HOMEMAKER") || s.equals("K12STUDENT") || s.equals("LAWYER")
                || s.equals("PROGRAMMER") || s.equals("RETIRED") || s.equals("SALES") || s.equals("MARKETING")
                || s.equals("SCIENTIST") || s.equals("SELFEMPLOYED") || s.equals("TECHNICIAN") || s.equals("ENGINEER")
                || s.equals("TRADESMAN") || s.equals("UNEMPLOYED") || s.equals("WRITER") || s.equals("CRAFTSMAN"))){
            return null;
        }

        if(s.equals("OTHER")){ return User.Occupation.OTHER; }
        if(s.equals("ACADEMIC" || s.equals("EDUCATOR"))){ return User.Occupation.ACADEMIC_OR_EDUCATOR; }
        if(s.equals("ARTIST")){ return User.Occupation.ARTIST; }
        if(s.equals("CLERICAL") || s.equals("ADMIN")){ return User.Occupation.CLERICAL_OR_ADMIN; }
        if(s.equals("COLLEGE") || s.equals("GRADSTUDENT")){ return User.Occupation.COLLEGE_OR_GRAD_STUDENT; }
        if(s.equals("CUSTOMERSERVICE")){ return User.Occupation.CUSTOMER_SERVICE; }
        if(s.equals("DOCTOR") ||s.equals("HEALTHCARE")){ return User.Occupation.DOCTOR_OR_HEALTH_CARE; }
        if(s.equals("EXECUTIVE") || s.equals("MANAGERIAL")){ return User.Occupation.EXECUTIVE_OR_MANAGERIAL; }
        if(s.equals("FARMER")){ return User.Occupation.FARMER; }
        if(s.equals("HOMEMAKER")){ return User.Occupation.HOMEMAKER; }
        if(s.equals("K12STUDENT")){ return User.Occupation.K_12_STUDENT; }
        if(s.equals("LAWYER")){ return User.Occupation.LAWYER; }
        if(s.equals("PROGRAMMER")){ return User.Occupation.PROGRAMMER; }
        if(s.equals("RETIRED")){ return User.Occupation.RETIRED; }
        if(s.equals("SALES") || s.equals("MARKETING")){ return User.Occupation.SALES_OR_MARKETING; }
        if(s.equals("SCIENTIST")){ return User.Occupation.SCIENTIST; }
        if(s.equals("SELFEMPLOYED")){ return User.Occupation.SELF_EMPLOYED; }
        if(s.equals("TECHNICIAN") || s.equals("ENGINEER")){ return User.Occupation.TECHNICIAN_OR_ENGINEER; }
        if(s.equals("TRADESMAN") || s.equals("CRAFTSMAN")){ return User.Occupation.TRADESMAN_OR_CRAFTSMAN; }
        if(s.equals("UNEMPLOYED")){ return User.Occupation.UNEMPLOYED; }
        if(s.equals("WRITER")){ return User.Occupation.WRITER; }

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
