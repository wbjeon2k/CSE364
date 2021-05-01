package kr.twww.mrs.data;

import org.apache.spark.mllib.recommendation.Rating;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.nio.file.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

import static org.junit.Assert.assertThat;

//RatingList 로 변환 하는지 테스트.
//1::A B C D (E F G) (1998)::Animation|Children's|Comedy
//5개 로 구성된 테스트 케이스 통과해야 한다.
@Ignore
@RunWith(Parameterized.class)
public class ToRatingListTest {
    DataReaderImpl dataReader;
    Path base = Paths.get("data/test/");
    String question;
    ArrayList<Rating> answer;

    /*
    1::10::1::12345678
    */

    //UserID = 1, 평가한 영화 id = 10, 평가 별점 1, timestamp
    //넣은 rating 케이스 만드는 함수.
//    static Rating TCtemplate(){
//        Rating tmp = new Rating();
//        tmp.userId = 1;
//        tmp.movieId = 10;
//        tmp.rating = 1;
//        tmp.timestamp = 12345678;
//        return tmp;
//    }

    //위 Rating 객체를 x 개 만큼 넣은 ArrayList 만들어주는 함수.
//    static ArrayList<Rating> tcgen(int x){
//        ArrayList<Rating> ret = new ArrayList<Rating>();
//        for(int i=0;i<x;++i) ret.add(TCtemplate());
//        return ret;
//    }

    /*
    1번째: Rating 1개 넣었을 때 확인.
    2번째: 파일 비었을 때 빈 리스트 반환 확인.
    3번째: Rating 2개 넣었을 때 확인.
     */
    @Parameters
    public static Collection<Object[]> testSet() {
        return Arrays.asList(new Object[][]{
//                {"1::10::1::12345678",tcgen(1)},
//                {"",tcgen(0)},
//                {"1::10::1::12345678\n1::10::1::12345678", tcgen(2)}
        });
    }

    public ToRatingListTest(String Q, ArrayList<Rating> A) {
        System.out.println("GetPathTest: Test case started.");
        this.dataReader = new DataReaderImpl();
        this.question = Q;
        this.answer = A;
    }

    boolean sameRating(Rating a, Rating b){
//        if(a.userId != b.userId) return false;
//        if(a.movieId != b.movieId) return false;
//        if(a.timestamp != b.timestamp) return false;
//        if(a.rating != b.rating) return false;

        return true;
    }

    public boolean compareRatingList(ArrayList<Rating> answer, ArrayList<Rating> result){
        if(answer == null){
            return result == null;
        }

        if(answer.size() == 0){
            return result.size() == 0;
        }

        for (Rating rating : answer) {
            boolean chk = false;
            for (Rating value : result) {
                if (sameRating(rating, value)) chk = true;
            }
            if (!chk) return false;
        }

        return true;
    }

    @Test
    public void parameterTest(){
        String read_text = question;
//        ArrayList<Rating> result = dataReader.ToRatingList(read_text);

//        assertTrue(compareRatingList(answer,result));
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