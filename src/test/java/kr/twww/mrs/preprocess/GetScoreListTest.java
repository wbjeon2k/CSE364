package kr.twww.mrs.preprocess;

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
public class GetScoreListTest {
    PreprocessorImpl dataPreprocessor;
    DataReaderImpl dataReader;

    String input_genres, input_occupation;
    double answer;

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
     */
    @Parameters
    public static Collection<Object[]> testSet() {
        return Arrays.asList(new Object[][]{
                {"Animation|Drama","grad_student", 3.5},
        });
    }

    public GetScoreListTest(String A, String B, double C){
        this.dataPreprocessor = new PreprocessorImpl();
        this.dataReader = new DataReaderImpl();

        this.input_genres = A;
        this.input_occupation = B;
        this.answer =  C;
    }

    @Test
    public void parametrizedTest(){
        ArrayList<Movie.Genre> genreList = dataPreprocessor.GetGenreList(input_genres);
        User.Occupation occupation = dataPreprocessor.GetOccupation(input_occupation);
        ArrayList<User> userList = user_list_gen();
        ArrayList<Movie> movieList = movie_list_gen();
        ArrayList<Rating> ratingList = ratings_list_gen();
        ArrayList<Rating> result = dataPreprocessor.GetScoreList(genreList,occupation,userList,movieList,ratingList);

        //rating 2개 넣었으므로 개수 2개 나오는지 확인.
        assertEquals(result.size(), 2);
        double sum=0.0;
        if(result.size() != 0){
            for(int i=0;i<result.size();++i){
                sum += result.get(i).rating;
            }
            sum /= result.size();
        }
        //예상 점수 같은지 확인.
        assertThat(sum, is(answer));
    }


    /*
    ArrayList<Movie.Genre> genreList,
    User.Occupation occupation,
    ArrayList<User> userList,
    ArrayList<Movie> movieList,
    ArrayList<Rating> ratingList
    */

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