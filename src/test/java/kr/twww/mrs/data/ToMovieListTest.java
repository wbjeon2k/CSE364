package kr.twww.mrs.data;

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


//MovieList 로 변환 하는지 테스트.
//1::A B C D é(E F G) (1998)::Animation|Children's|Comedy
//5개 로 구성된 테스트 케이스 통과해야 한다.
@RunWith(Parameterized.class)
public class ToMovieListTest {
    DataReaderImpl dataReader;
    String question;
    ArrayList<Movie> answer;

    //movie TC template.
    static Movie TCtemplate(){
        Movie tmp = new Movie();
        tmp.genres = new ArrayList<Movie.Genre>();
        tmp.genres.add(Movie.Genre.Animation);
        tmp.genres.add(Movie.Genre.Children_s);
        tmp.genres.add(Movie.Genre.Comedy);
        tmp.movieId = 1;
        tmp.title = "A B C D é(E F G) (1998)";
        return tmp;
    }

    //tctemplate 을 x개 만큼 채운 ArrayList를 정답으로 만들기.
    static ArrayList<Movie> tcgen(int x){
        ArrayList<Movie> ret = new ArrayList<Movie>();
        for(int i=0;i<x;++i) ret.add(TCtemplate());
        return ret;
    }

    @Parameters
    public static Collection<Object[]> testSet() {
        return Arrays.asList(new Object[][]{
                {"1::A B C D é(E F G) (1998)::Animation|Children's|Comedy", tcgen(1)},
                {"", tcgen(0)},
                {"1::A B C D é(E F G) (1998)::Animation|Children's|Comedy 1::A B C D é(E F G) (1998)::Animation|Children's|Comedy", tcgen(2)},
                {"1::A B C D é(E F G) (1998)::Animat", null}
        });
    }

    public ToMovieListTest(String Q, ArrayList<Movie> A) {
        System.out.println("GetPathTest: Test case started.");
        this.dataReader = new DataReaderImpl();
        this.question = Q;
        this.answer = A;
    }

    @Test
    public void parameterTest(){

        String read_text = question;
        ArrayList<Movie> result = dataReader.ToMovieList(read_text);
        assertEquals(result, answer);
        /*
        public int movieId;
        public String title;
        public ArrayList<Genre> genres;
        1 A B C D é(E F G) (1998) Animation|Children's|Comedy
        */

    }

    @Before
    public void setUp() throws Exception
    {
        System.out.println("Starting ToMovieListTest!");
        dataReader = new DataReaderImpl();
    }

    @After
    public void tearDown() throws Exception
    {
        System.out.println("Finishing ToMovieListTest!");
        dataReader = null;
    }

}
