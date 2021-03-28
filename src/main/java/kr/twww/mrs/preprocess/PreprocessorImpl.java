package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PreprocessorImpl extends PreprocessorBase implements Preprocessor
{
    DataReader dataReader;

    public PreprocessorImpl()
    {
        dataReader = new DataReaderImpl();
    }

    @Override
    public ArrayList<Rating> GetScoreList( String _category, String _occupation )
    {
        var genreList = GetGenreList(_category);
        var occupation = GetOccupation(_occupation);

        var result = GetScoreList(
                genreList,
                occupation,
                dataReader.GetUserList(),
                dataReader.GetMovieList(),
                dataReader.GetRatingList()
        );

        return result;
    }

    @Override
    public ArrayList<Movie.Genre> GetGenreList( String genreText )
    {
        // TODO: 주어진 텍스트를 enum Genre 리스트로 반환
        String s = genreText;
        s = s.toLowerCase();    // 소문자로 통일
        s = s.replaceAll("\\p{Z}","");  //  공백제거
        s = s.replaceAll("|","A"); // '|' 를 대문자 A로 치환

        ArrayList<String> list = new ArrayList<String>();   // 장르를 여러개 받을수있는 리스트선언
        String[] getstr1 = s.split("A");        // 장르를 대문자 A를 기준으로 구분해서 배열에 담음

        for(int i=0; i<getstr1.length;i++){     // 리스트에 split한 장르를 다시 모아줌
            list.add(getstr1[i]);
        }
        for(int j=0; j<list.size();j++){    // 주어진 장르에 포함되는것이 없으면 에러
            if(!(list.get(j).equals("action") || list.get(j).equals("adventure") || list.get(j).equals("animation")
                    || list.get(j).equals("childrens") || list.get(j).equals("comedy") || list.get(j).equals("crime")
                    || list.get(j).equals("documentary") || list.get(j).equals("drama") || list.get(j).equals("fantasy")
                    || list.get(j).equals("filmnoir") || list.get(j).equals("horror") || list.get(j).equals("musical")
                    || list.get(j).equals("mystery") || list.get(j).equals("romance") || list.get(j).equals("scifi")
                    || list.get(j).equals("thriller") || list.get(j).equals("war") || list.get(j).equals("western")))
                return null;
        }
        ArrayList<Movie.Genre> movie_list = new ArrayList<Movie.Genre>();   // 주어진 장르종류를 담아줄 리스트
        for(int k=0; k<list.size();k++){    // 맞는 장르 찾아서 리스트에 추가
            if(list.get(k).equals("action")){ movie_list.add(Movie.Genre.Action);}
            if(list.get(k).equals("adventure")){ movie_list.add(Movie.Genre.Adventure);}
            if(list.get(k).equals("animation")){ movie_list.add(Movie.Genre.Animation);}
            if(list.get(k).equals("children_s")){ movie_list.add(Movie.Genre.Children_s);}
            if(list.get(k).equals("comedy")){ movie_list.add(Movie.Genre.Comedy);}
            if(list.get(k).equals("crime")){ movie_list.add(Movie.Genre.Crime);}
            if(list.get(k).equals("documentary")){ movie_list.add(Movie.Genre.Documentary);}
            if(list.get(k).equals("drama")){ movie_list.add(Movie.Genre.Drama);}
            if(list.get(k).equals("fantasy")){ movie_list.add(Movie.Genre.Fantasy);}
            if(list.get(k).equals("filmnoir")){ movie_list.add(Movie.Genre.Film_Noir);}
            if(list.get(k).equals("horror")){ movie_list.add(Movie.Genre.Horror);}
            if(list.get(k).equals("musical")){ movie_list.add(Movie.Genre.Musical);}
            if(list.get(k).equals("mystery")){ movie_list.add(Movie.Genre.Mystery);}
            if(list.get(k).equals("romance")){ movie_list.add(Movie.Genre.Romance);}
            if(list.get(k).equals("scifi")){ movie_list.add(Movie.Genre.Sci_Fi);}
            if(list.get(k).equals("thriller")){ movie_list.add(Movie.Genre.Thriller);}
            if(list.get(k).equals("war")){ movie_list.add(Movie.Genre.War);}
            if(list.get(k).equals("western")){ movie_list.add(Movie.Genre.Western);}
        }

        return movie_list;      // 장르 모은 리스트 반환

    }

    @Override
    public User.Occupation GetOccupation( String occupationText )
    {
        // TODO: 주어진 텍스트를 enum Occupation으로 반환

        String s = occupationText;
        s = s.replaceAll("\\p{Z}","");  // 공백제거
        s = s.replaceAll("\\p{Punct}","");  // 특수문자제거
        s = s.toUpperCase();    // 대문자로 통일
        // 형식에서 벗어나는 텍스트가 들어오면 오류
        if(!(s.equals("OTHER") || s.equals("ACADEMIC") || s.equals("EDUCATOR") || s.equals("ARTIST") || s.equals("CLERICAL")
                || s.equals("GRADSTUDENT") || s.equals("COLLEGE") || s.equals("ADMIN") || s.equals("CUSTOMERSERVICE")
                || s.equals("DOCTOR") || s.equals("HEALTHCARE") || s.equals("EXECUTIVE") || s.equals("MANAGERIAL")
                || s.equals("FARMER") || s.equals("HOMEMAKER") || s.equals("K12STUDENT") || s.equals("LAWYER")
                || s.equals("PROGRAMMER") || s.equals("RETIRED") || s.equals("SALES") || s.equals("MARKETING")
                || s.equals("SCIENTIST") || s.equals("SELFEMPLOYED") || s.equals("TECHNICIAN") || s.equals("ENGINEER")
                || s.equals("TRADESMAN") || s.equals("UNEMPLOYED") || s.equals("WRITER") || s.equals("CRAFTSMAN"))){
            return null;
        }
        // 정리한 알파벳과 매칭되는 직업 뱉어줌
        if(s.equals("OTHER")){ return User.Occupation.OTHER; }
        if(s.equals("ACADEMIC") || s.equals("EDUCATOR")){ return User.Occupation.ACADEMIC_OR_EDUCATOR; }
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
    public ArrayList<Rating> GetScoreList(
            ArrayList<Movie.Genre> genreList,
            User.Occupation occupation,
            ArrayList<User> userList,
            ArrayList<Movie> movieList,
            ArrayList<Rating> ratingList
    )
    {
        ArrayList<Movie> movie_list = new ArrayList<Movie>();   // 장르에 맞는 movie 넣을 list
        ArrayList<User> user_list = new ArrayList<User>();      // 직업맞는 user 넣을 list
        ArrayList<Rating> rating_list = new ArrayList<Rating>(); // userid movieid rating이 전부 들어있는 list


        return null;
    }
}

