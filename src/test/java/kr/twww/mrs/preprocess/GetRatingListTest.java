package kr.twww.mrs.preprocess;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.nio.file.*;

import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import static org.junit.Assert.*;

import kr.twww.mrs.data.*;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class GetRatingListTest {
    PreprocessorImpl dataPreprocessor;
    DataReaderImpl dataReader;

    String input_genres, input_occupation;
    ArrayList<Rating> answer;

    //UserId = 1, gender = female, age = under_18, occupation = COLLEGE_OR_GRAD_STUDENT, zipcode = "48067"
    //들어가는 user 객체 만드는  함수.
    static User user_gen(){
        //1::F::1::4::48067
        User ret = new User();
        ret.userId = 1;
        ret.gender = User.Gender.FEMALE;
        ret.age = User.Age.UNDER_18;
        ret.occupation = User.Occupation.COLLEGE_OR_GRAD_STUDENT;
        ret.zipCode = "48067";
        return ret;
    }

    //User 1개 넣은 userlist 만드는 함수.
    static ArrayList<User> user_list_gen(){
        ArrayList<User> ret = new ArrayList<>();
        ret.add(user_gen());
        return ret;
    }

    /*
    1::Toy Story (1995)::Animation|Drama
    2::Jumanji (1995)::Animation|Drama
     */

    //1::Toy Story (1995)::Animation|Drama
    //들어간 Movie 객체 만드는 함수.
    static Movie toystory_gen(){
        Movie ret = new Movie();
        ret.title = "Toy Story (1995)";
        ret.movieId = 1;
        ret.genres = new ArrayList<>();
        ret.genres.add(Movie.Genre.Animation);
        ret.genres.add(Movie.Genre.Drama);
        return ret;
    }

    //2::Jumanji (1995)::Animation|Drama
    //들어간 Movie 객체 만드는 함수.
    static Movie jumanji_gen(){
        Movie ret = new Movie();
        ret.title = "Jumanji (1995)";
        ret.movieId = 2;
        ret.genres = new ArrayList<>();
        ret.genres.add(Movie.Genre.Animation);
        ret.genres.add(Movie.Genre.Drama);
        return ret;
    }

    //토이스토리, 쥬만지 영화 2개 넣은 movielist 반환.
    static ArrayList<Movie> movie_list_gen(){
        ArrayList<Movie> ret = new ArrayList<>();
        ret.add(toystory_gen());
        ret.add(jumanji_gen());
        return ret;
    }

    /*
    1::1::4::978300760
    1::2::3::978302109
     */

    //토이스토리에 4점 준 Rating 객체 반환.
    static Rating toy_rating_gen(){
        Rating ret = new Rating();
        ret.userId = 1;
        ret.movieId = 1;
        ret.rating = 4;
        ret.timestamp = 978300760;
        return ret;
    }

    //쥬만지에 4점 준 Rating 객체 반환.
    static Rating jumanji_rating_gen(){
        Rating ret = new Rating();
        ret.userId = 1;
        ret.movieId = 2;
        ret.rating = 3;
        ret.timestamp = 978302109;
        return ret;
    }

    //토이스토리에 4점, 쥬만지에 3점 준
    //Rating 2개 들어간 리스트 반환.
    static ArrayList<Rating> ratings_list_gen(){
        ArrayList<Rating> ret = new ArrayList<>();
        ret.add(toy_rating_gen());
        ret.add(jumanji_rating_gen());
        return ret;
    }

    //parameter
    /*
    1번째: 장르, 2번째: 직업, 3번째: 예상 점수
    1: 토이스토리, 쥬만지 2개 들어간 리스트 반환
    2: 1과 똑같은 결과. 장르 순서만 바뀐것
     */
    @Parameters
    public static Collection<Object[]> testSet() {
        return Arrays.asList(new Object[][]{
                {"Animation|Drama","grad_student", ratings_list_gen() },
                {"Drama|Animation","grad_student", ratings_list_gen() },
                {"Animation|Drama","gRaD Student", ratings_list_gen() },
                {"Animation|Drama","grad student", ratings_list_gen() },
                {"Animation|Drama","K-12 student", new ArrayList<Rating>() },
                {"Animation|Drama","K-12student", new ArrayList<Rating>() },
                {"Animation|Drama","JeonWoongbae", null }
        });
    }

    public GetRatingListTest(String A, String B, ArrayList<Rating> C){
        this.dataPreprocessor = new PreprocessorImpl();
        this.dataReader = new DataReaderImpl();

        this.input_genres = A;
        this.input_occupation = B;
        this.answer =  C;
    }

    boolean sameRating(Rating a, Rating b){
        if(a.userId != b.userId) return false;
        if(a.movieId != b.movieId) return false;
        if(a.timestamp != b.timestamp) return false;
        if(a.rating != b.rating) return false;

        return true;
    }

    public boolean compareRatingList(ArrayList<Rating> answer, ArrayList<Rating> result){
        if(answer == null){
            return result == null;
        }

        if(answer.size() == 0){
            return result.size() == 0;
        }

        for (Rating rating : answer) {
            boolean chk = false;
            for (Rating value : result) {
                if (sameRating(rating, value)) chk = true;
            }
            if (!chk) return false;
        }

        return true;
    }

    @Ignore
    @Test
    public void parametrizedTest(){
        ArrayList<Movie.Genre> genreList = dataPreprocessor.GetGenreList(input_genres);
        User.Occupation occupation = dataPreprocessor.GetOccupation(input_occupation);
        ArrayList<User> userList = user_list_gen();
        ArrayList<Movie> movieList = movie_list_gen();
        ArrayList<Rating> ratingList = ratings_list_gen();
        ArrayList<Rating> result = dataPreprocessor.GetScoreList(genreList,occupation,userList,movieList,ratingList);
        //
        assertTrue(compareRatingList(answer,result));
    }

    @Before
    public void setUp() throws Exception
    {
        System.out.println("Starting GetScoreList test!");
        dataPreprocessor = new PreprocessorImpl();
    }

    @After
    public void tearDown() throws Exception
    {
        System.out.println("Finishing GetScoreList test!");
        dataPreprocessor = null;
    }


}