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
public class GetGenreListTest {
    PreprocessorImpl dataPreprocessor;
    String question;
    ArrayList<Movie.Genre> answer;

    @Parameters
    public static Collection<Object[]> testSet() {
        return Arrays.asList(new Object[][]{
                {"comedy|animation", Arrays.asList(Movie.Genre.Comedy, Movie.Genre.Animation)},
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
        assertEquals(result, answer);
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