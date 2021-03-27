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

@RunWith(Parameterized.class)
public class GetScoreListTest {
    PreprocessorImpl dataPreprocessor;
    DataReaderImpl dataReader;

    String input_genres, input_occupation;

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

    static ArrayList<User> user_list_gen(){
        ArrayList<User> ret = new ArrayList<>();
        ret.add(user_gen());
        return ret;
    }

    /*
    1::Toy Story (1995)::Animation|Drama
    2::Jumanji (1995)::Animation|Drama
     */

    static Movie toystory_gen(){
        Movie ret = new Movie();
        ret.title = "Toy Story (1995)";
        ret.movieId = 1;
        ret.genres.add(Movie.Genre.Animation);
        ret.genres.add(Movie.Genre.Drama);
        return ret;
    }

    static Movie jumanji_gen(){
        Movie ret = new Movie();
        ret.title = "Jumanji (1995)";
        ret.movieId = 2;
        ret.genres.add(Movie.Genre.Animation);
        ret.genres.add(Movie.Genre.Drama);
        return ret;
    }

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
    static Rating toy_rating_gen(){
        Rating ret = new Rating();
        ret.userId = 1;
        ret.movieId = 1;
        ret.rating = 4;
        ret.timestamp = 978300760;
        return ret;
    }

    static Rating jumanji_rating_gen(){
        Rating ret = new Rating();
        ret.userId = 1;
        ret.movieId = 2;
        ret.rating = 3;
        ret.timestamp = 978302109;
        return ret;
    }

    static ArrayList<Rating> ratings_list_gen(){
        ArrayList<Rating> ret = new ArrayList<>();
        ret.add(toy_rating_gen());
        ret.add(jumanji_rating_gen());
        return ret;
    }

    //parameter 2개 :
    @Parameters
    public static Collection<Object[]> testSet() {
        return Arrays.asList(new Object[][]{
                {"Animation|Drama","grad_student"},
        });
    }

    public GetScoreListTest(String A, String B){
        this.dataPreprocessor = new PreprocessorImpl();
        this.dataReader = new DataReaderImpl();

        input_genres = A;
        input_occupation = B;
    }

    @Test
    public void parametrizedTest(){
        ArrayList<Movie.Genre> genreList = dataPreprocessor.GetGenreList(input_genres);
        User.Occupation occupation = dataPreprocessor.GetOccupation(input_occupation);
        ArrayList<User> userList = user_list_gen();
        ArrayList<Movie> movieList = movie_list_gen();
        ArrayList<Rating> ratingList = ratings_list_gen();
        ArrayList<Score> result = dataPreprocessor.GetScoreList(genreList,occupation,userList,movieList,ratingList);
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