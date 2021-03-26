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

//RatingList 로 변환 하는지 테스트.
//1::A B C D (E F G) (1998)::Animation|Children's|Comedy
//5개 로 구성된 테스트 케이스 통과해야 한다.
@RunWith(Parameterized.class)
public class ToRatingListTest {
    DataReaderImpl dataReader;
    Path base = Paths.get("data/test/");
    String question;
    ArrayList<Rating> answer;

    /*
    1::10::1::12345678
    */

    static Rating TCtemplate(){
        Rating tmp = new Rating();
        tmp.userId = 1;
        tmp.movieId = 10;
        tmp.rating = 1;
        tmp.timestamp = 12345678;
        return tmp;
    }

    static ArrayList<Rating> tcgen(int x){
        ArrayList<Rating> ret = new ArrayList<Rating>();
        for(int i=0;i<x;++i) ret.add(TCtemplate());
        return ret;
    }

    @Parameters
    public static Collection<Object[]> testSet() {
        return Arrays.asList(new Object[][]{
                {"1::10::1::12345678",tcgen(1)},
                {"",tcgen(0)},
                {"1::10::1::12345678 1::10::1::12345678", tcgen(2)}
        });
    }

    public ToRatingListTest(String Q, ArrayList<Rating> A) {
        System.out.println("GetPathTest: Test case started.");
        this.dataReader = new DataReaderImpl();
        this.question = Q;
        this.answer = A;
    }

    @Test
    public void parameterTest(){
        String read_text = question;
        ArrayList<Rating> result = dataReader.ToRatingList(read_text);
        assertEquals(result, answer);
    }

    @Before
    public void setUp() throws Exception
    {
        System.out.println("Starting ToRatingListTest!");
        dataReader = new DataReaderImpl();
    }

    @After
    public void tearDown() throws Exception
    {
        System.out.println("Starting ToRatingListTest!");
        dataReader = null;
    }
}