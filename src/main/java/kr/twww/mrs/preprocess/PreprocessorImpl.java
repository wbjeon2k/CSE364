package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.*;
import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.Rating;
import kr.twww.mrs.data.object.User;
import kr.twww.mrs.preprocess.object.Score;

import java.util.ArrayList;

public class PreprocessorImpl extends PreprocessorBase implements Preprocessor
{
    DataReader dataReader;

    public PreprocessorImpl()
    {
        dataReader = new DataReaderImpl();
    }

    @Override
    public ArrayList<Score> GetRecommendList(String _gender, String _age, String _occupation )
    {
        return GetRecommendList(_gender, _age, _occupation, "");
    }

    @Override
    public ArrayList<Score> GetRecommendList( String _gender, String _age, String _occupation, String _categories )
    {
        var gender = User.ConvertGender(_gender);
        var age = User.ConvertAge(_age);
        var occupation = User.ConvertOccupationByText(_occupation);
        var categoryList = GetCategoryList(_categories);

        return GetScoreList(
                gender,
                age,
                occupation,
                categoryList,
                dataReader.GetUserList(),
                dataReader.GetMovieList(),
                dataReader.GetRatingList(),
                dataReader.GetLinkList()
        );
    }

    @Override
    public ArrayList<Movie.Genre> GetCategoryList( String genreText )
    {
        String s = genreText;
        s = s.toLowerCase();    // 소문자로 통일
        s = s.replaceAll("\\p{Z}","");  //  공백제거
        s = s.replaceAll("\\|","A"); // '|' 를 대문자 A로 치환

        s = s.replaceAll("[^a-zA-Z]", "");

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
            if(list.get(k).equals("action")){ movie_list.add(Movie.Genre.ACTION);}
            if(list.get(k).equals("adventure")){ movie_list.add(Movie.Genre.ADVENTURE);}
            if(list.get(k).equals("animation")){ movie_list.add(Movie.Genre.ANIMATION);}
            if(list.get(k).equals("childrens")){ movie_list.add(Movie.Genre.CHILDREN_S);}
            if(list.get(k).equals("comedy")){ movie_list.add(Movie.Genre.COMEDY);}
            if(list.get(k).equals("crime")){ movie_list.add(Movie.Genre.CRIME);}
            if(list.get(k).equals("documentary")){ movie_list.add(Movie.Genre.DOCUMENTARY);}
            if(list.get(k).equals("drama")){ movie_list.add(Movie.Genre.DRAMA);}
            if(list.get(k).equals("fantasy")){ movie_list.add(Movie.Genre.FANTASY);}
            if(list.get(k).equals("filmnoir")){ movie_list.add(Movie.Genre.FILM_NOIR);}
            if(list.get(k).equals("horror")){ movie_list.add(Movie.Genre.HORROR);}
            if(list.get(k).equals("musical")){ movie_list.add(Movie.Genre.MUSICAL);}
            if(list.get(k).equals("mystery")){ movie_list.add(Movie.Genre.MYSTERY);}
            if(list.get(k).equals("romance")){ movie_list.add(Movie.Genre.ROMANCE);}
            if(list.get(k).equals("scifi")){ movie_list.add(Movie.Genre.SCI_FI);}
            if(list.get(k).equals("thriller")){ movie_list.add(Movie.Genre.THRILLER);}
            if(list.get(k).equals("war")){ movie_list.add(Movie.Genre.WAR);}
            if(list.get(k).equals("western")){ movie_list.add(Movie.Genre.WESTERN);}
        }

        return movie_list;      // 장르 모은 리스트 반환

    }

    @Override
    public ArrayList<Score> GetScoreList(
            User.Gender gender,
            User.Age age,
            User.Occupation occupation,
            ArrayList<Movie.Genre> genreList,
            ArrayList<User> userList,
            ArrayList<Movie> movieList,
            ArrayList<Rating> ratingList,
            ArrayList<Link> linkList
    )
    {
        if ( gender == null ) return null;
        if ( age == null ) return null;
        if ( occupation == null ) return null;
        if ( genreList == null ) return null;
        if ( userList == null ) return null;
        if ( movieList == null ) return null;
        if ( ratingList == null ) return null;
        if ( linkList == null ) return null;

        // TODO

        return null;
    }
}

