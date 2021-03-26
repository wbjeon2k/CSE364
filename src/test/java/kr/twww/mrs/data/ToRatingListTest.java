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
    Path question, answer;

    @Parameters
    public static Collection<Object[]> testSet() {
        return Arrays.asList(new Object[][]{
                {"testRating1.dat", "resultRating1.dat"},
                {"error_path1.dat", ""},
                {"error_data_rating1", ""}
        });
    }

    public ToRatingListTest(String Q, String A) {
        System.out.println("GetPathTest: Test case started.");
        this.dataReader = new DataReaderImpl();
        this.question = base.resolve(Q);
        this.answer = base.resolve(A);
    }

    //잘못된 경로 들어오면 ToRatingList 는 null return.
    @Test
    public void error_path_Test(){
        String q = question.toString();
        if(!q.contains("error_path")) return;

        String read_text = dataReader.ReadTextFromFile(question.toString());
        ArrayList<Rating> result = dataReader.ToRatingList(read_text);
        assertNull(result);
    }

    //잘못된 데이터 들어오면 ToRatingList null return.
    @Test
    public void error_data_Test() {
        String q = question.toString();
        if (!q.contains("error_data")) return;

        String read_text = dataReader.ReadTextFromFile(question.toString());
        ArrayList<Rating> result = dataReader.ToRatingList(read_text);
        assertNull(result);
    }


    @Test
    public void parameterTest(){
        String q = question.toString();
        if(!q.contains("test")) return;

        String read_text = dataReader.ReadTextFromFile(question.toString());
        ArrayList<Rating> result = dataReader.ToRatingList(read_text);

        assertEquals(result.size(), 5);
        /*
        public int userId;
        public int movieId;
        public int rating;
        public int timestamp;
        */
        for(int i=0;i< result.size();++i){
            Rating now = result.get(i);
            assertEquals(now.userId, 1);
            assertEquals(now.movieId, i+1);
            assertEquals(now.rating, i+1);
            assertEquals(now.timestamp, 12345678);
        }
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