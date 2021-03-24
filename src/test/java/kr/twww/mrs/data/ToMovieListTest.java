package kr.twww.mrs.data;

import org.junit.Test;
import org.junit.runner.RunWith;
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
    Path base = Paths.get("data/test/");
    Path question, answer;

    @Parameters
    public static Collection<Object[]> testSet() {
        return Arrays.asList(new Object[][]{
                {"testMovie1.dat", "resultMovie1.dat"},
        });
    }

    public ToMovieListTest(String Q, String A) {
        System.out.println("GetPathTest: Test case started.");
        this.dataReader = new DataReaderImpl();
        question = base.resolve(Q);
        answer = base.resolve(A);
    }

    @Test
    public void parameterTest(){
        String read_text = dataReader.ReadTextFromFile(question.toString());
        ArrayList<Movie> result = dataReader.ToMovieList(read_text);

        assertEquals(result.size(), 5);

        /*
        public int movieId;
        public String title;
        public ArrayList<Genre> genres;
        1 "A B C D é(E F G) (1998)" Animation|Children's|Comedy
        */
        Movie.Genre[] genres = {Movie.Genre.Animation, Movie.Genre.Children_s, Movie.Genre.Comedy};

        for(int i=0;i < result.size();++i){
            Movie now = result.get(i);
            assertEquals(now.movieId, i+1);
            assertEquals(now.title, "A B C D (E F G) (1998)");
            assertEquals(now.genres.size(), 3);
            for(int j=0;j<now.genres.size();++j){
                assertEquals(now.genres.get(j), genres[j]);
            }
        }
    }

}
