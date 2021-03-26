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
    public ArrayList<Score> GetScoreList(
            ArrayList<Movie.Genre> genreList,
            User.Occupation occupation,
            ArrayList<User> userList,
            ArrayList<Movie> movieList,
            ArrayList<Rating> ratingList
    )
    {
        ArrayList<Movie> movie_list = new ArrayList<Movie>();   // 장르에 맞는 movie 넣을 list
        ArrayList<Integer> movie_id = new ArrayList<Integer>(); // 그중에서 movieid만 모을 list
        ArrayList<User> user_list = new ArrayList<User>();      // 직업맞는 user 넣을 list
        ArrayList<Integer> user_id = new ArrayList<Integer>();  // 그중에서 userid만 모을 list
        ArrayList<Rating> rating_list = new ArrayList<Rating>(); // userid movieid rating이 전부 들어있는 list
        ArrayList<Integer> score_list = new ArrayList<Integer>(); // 그중에서 rating만 모을 list

        // genreList에서 get으로 한개씩 불러와서 movieList에서 장르가 일치하면 movie_list에 넣으려고했는데
        // 자꾸 안됨.. movie_list에 장르로 걸러서 모을수만있으면 그중에서 movie_id만 모을 예정

        // 위와 마찬가지 이유로 UserList에서 user_list로 직업이 맞을때 나머지 정보를 포함해서 넣는게 안됨.
        // user_list에 직업이 일치하는 user 정보가 전부 들어오면 그중에서 user_id만 모으면 될듯

        // user_id와 movie_id가 겹치는 것에 해당되는 ratingList의 userid movieid rating timestamp전체를 다 들고와서
        // rating_list에 넣으면 그중에서 rating만 모아서 score_list에 넣으면되는데 ...

        //모든 문제의 원인 단순 배열이면 get()함수를 통해서 숫자를 늘려가면 전체탐색을 할텐데
        //우리가 선언한 User Movie Rating 처럼 여러자료형의 변수가 같이 들어있을경우 탐색을 어떻게하고
        //찾은 자료형 이외에 같은 정보인 다른 자료형의 데이터를 같이 추가할수있는지 모르겠음.

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

