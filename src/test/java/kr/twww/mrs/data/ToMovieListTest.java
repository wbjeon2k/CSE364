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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

//MovieList 로 변환 하는지 테스트.
//1::A B C D eee(E F G) (1998)::Animation|Children's|Comedy
//5개 로 구성된 테스트 케이스 통과해야 한다.
@RunWith(Parameterized.class)
public class ToMovieListTest {
    DataReaderImpl dataReader;
    String question;
    ArrayList<Movie> answer;

    //movie TC template.
    //ID:1, title: "A B C D eee(E F G) (1998)", genres: Animation|Children's|Comedy
    //들어가는 Movie 케이스 만드는 함수.
    static Movie TCtemplate(){
        Movie tmp = new Movie();
        tmp.genres = new ArrayList<Movie.Genre>();
        tmp.genres.add(Movie.Genre.Animation);
        tmp.genres.add(Movie.Genre.Children_s);
        tmp.genres.add(Movie.Genre.Comedy);
        tmp.movieId = 1;
        tmp.title = "A B C D eee(E F G) (1998)";
        return tmp;
    }

    //위 tctemplate 을 x개 만큼 채운 ArrayList를 정답으로 만들기.
    static ArrayList<Movie> tcgen(int x){
        ArrayList<Movie> ret = new ArrayList<Movie>();
        for(int i=0;i<x;++i) ret.add(TCtemplate());
        return ret;
    }

    /*
    1번째: 영화 1개 넣었을때 확인.
    2번째: 빈 문자열 넣었을때 빈 리스트 반환하는지 확인.
    3번째: 영화 2개 넣었을때 확인.
    4번째: 잘못된 파일 형식 들어오면 null 반환하는지 확인.
     */
    @Parameters
    public static Collection<Object[]> testSet() {
        return Arrays.asList(new Object[][]{
                {"", tcgen(0)},
                {"1::A B C D eee(E F G) (1998)::Animation|Children's|Comedy", tcgen(1)},
                {"1::A B C D eee(E F G) (1998)::Animation|Children's|Comedy\n1::A B C D eee(E F G) (1998)::Animation|Children's|Comedy", tcgen(2)},
                {"1::A B C D eee(E F G) (1998)::Animat", null}
        });
    }

    public ToMovieListTest(String Q, ArrayList<Movie> A) {
        System.out.println("GetPathTest: Test case started.");
        this.dataReader = new DataReaderImpl();
        this.question = Q;
        this.answer = A;
    }

    public boolean sameMovie(Movie a, Movie b){
        if(a.movieId != b.movieId) return false;
        if(!a.title.equals(b.title)) return false;
        if(a.genres.size() != b.genres.size()) return false;
        for(int i=0;i<a.genres.size();++i){
            if(!b.genres.contains(a.genres.get(i))) return false;
        }
        return true;
    }

    public boolean compareAnsRes(ArrayList<Movie> answer, ArrayList<Movie> result){
        if(answer == null){
            return result == null;
        }

        if(answer.size() == 0){
            return result.size() == 0;
        }

        for (Movie movie : answer) {
            boolean chk = false;
            for (Movie value : result) {
                if (sameMovie(movie, value)) chk = true;
            }
            if (!chk) return false;
        }

        return true;
    }

    @Test
    public void parameterTest(){

        String read_text = question;
        ArrayList<Movie> result = dataReader.ToMovieList(read_text);

        assertTrue(compareAnsRes(answer,result));
        /*
        public int movieId;
        public String title;
        public ArrayList<Genre> genres;
        1 A B C D eee(E F G) (1998) Animation|Children's|Comedy
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
