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
public class GetGenreListTest {
    PreprocessorImpl dataPreprocessor;
    String question;
    ArrayList<Movie.Genre> answer;
    //1번째 파라미터= question: 정상적으로 처리 되었다면 주어질 input
    //2번째 파라미터= answer: 정상적으로 작동하면 만들 결과.



    /*
    1번째: comedy, action 두 개 들어간 리스트 만드는지 확인.
    2번째~: 형태가 맞지 않으면 null return 확인.
     */
    @Parameters
    public static Collection<Object[]> testSet() {
        return Arrays.asList(new Object[][]{
                {"comedy|animation", new ArrayList<>(Arrays.asList(Movie.Genre.Comedy, Movie.Genre.Animation))},
                {"", null},
                {"com|ani", null}
        });
    }

    public GetGenreListTest(String Q, ArrayList<Movie.Genre> A){
        this.dataPreprocessor = new PreprocessorImpl();
        this.question = Q;
        this.answer = A;
    }

    @Test
    public void parameterTest(){
        ArrayList<Movie.Genre> result = dataPreprocessor.GetGenreList(question);
        for(int i=0;i<answer.size();++i){
            assert(result.contains(answer.get(i)));
        }
    }

    @Before
    public void setUp() throws Exception
    {
        System.out.println("Starting GetGenreList test!");
        dataPreprocessor = new PreprocessorImpl();
    }

    @After
    public void tearDown() throws Exception
    {
        System.out.println("Finishing GetGenreList test!");
        dataPreprocessor = null;
    }

}