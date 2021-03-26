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

    static Path base = Paths.get("data/test/pre_test/");
    String question;
    float answer;

    Path input, user_dat, movies_dat, ratings_dat;

    @Parameters
    public static Collection<Object[]> testSet() {
        return Arrays.asList(new Object[][]{
                {"pre_test1_", 3.5},
        });
    }

    public GetScoreListTest(String Q, float A){
        this.dataPreprocessor = new PreprocessorImpl();
        this.dataReader = new DataReaderImpl();

        question = Q;
        answer = A;

        input = base.resolve(Q + "input.dat");
        user_dat = base.resolve(Q + "user.dat");
        movies_dat = base.resolve(Q + "movies.dat");
        ratings_dat = base.resolve(Q + "ratings.dat");
    }

    @Test
    public void parametrizedTest(){
        FileInputStream ifstream;
        try{
            ifstream = new FileInputStream(input.toString());
        }
        catch (FileNotFoundException e){
            System.out.println("There is no " + input.toString() + " file!");
            return;
        }

        Scanner scanner = new Scanner(ifstream);

        String args1, args2;
        args1 = scanner.next();
        args2 = scanner.next();

        //GetScoreList 에 필요한 pameter 5개 순서대로 구한다.
        ArrayList<Movie.Genre> genres_list = dataPreprocessor.GetGenreList(args1);
        User.Occupation occupation = dataPreprocessor.GetOccupation(args2);
        ArrayList<User> user_list = dataReader.ToUserList(dataReader.ReadTextFromFile(user_dat.toString()));
        ArrayList<Movie> movies_list = dataReader.ToMovieList(dataReader.ReadTextFromFile(movies_dat.toString()));
        ArrayList<Rating> ratings_list = dataReader.ToRatingList(dataReader.ReadTextFromFile(ratings_dat.toString()));

        ArrayList<Score> scores_list = dataPreprocessor.GetScoreList(genres_list, occupation, user_list, movies_list, ratings_list);

        float sum = 0;
        if(!scores_list.isEmpty()){
            for(int i=0;i<scores_list.size();++i){
                sum += scores_list.get(i).GetScore();
            }
            sum /= (float)(scores_list.size());
        }

        //assert average
        assertEquals(sum, answer);
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