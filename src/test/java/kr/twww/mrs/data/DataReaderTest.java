package kr.twww.mrs.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.*;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import static org.junit.Assert.*;

/***
 * 원래 skeleton
 */


//기본 datareadertest 는 어떻게 써야하는걸까?
public class DataReaderTest
{
    DataReaderImpl dataReader;

    @Before
    public void setUp() throws Exception
    {
       dataReader = new DataReaderImpl();
    }

    @After
    public void tearDown() throws Exception
    {
       dataReader = null;
    }

    @Test
    public void getUserList()
    {
        assertTrue(true);
    }

}


/**
 * Parametrized test 초기 시안.
 * 이런 형태로 다른 테스트들도 진행하면 될지 확인 필요!
 * //원래 dataReader 과 테스트를 어떻게 연결하면 효율적일까?
 * //원래 dataReader 의 path 에 어떻게 접근 & 수정?
 * //https://github.com/wbjeon2k/CSE364/issues/8#issuecomment-804520940
 * //하위 메소드 바로 접근해서 해결 + 개별 하위 메소드 테스트.
 *
 * https://ildann.tistory.com/5
 * // DataReader test
 * 1. 잘 읽어낸 텍스트 반환
 * 2. 잘못된 파일이라 null 반환
 * 3. 잘못된 파일이라 없는 파일 읽기 시도 -> 오류(크래시)
 *
 * TODO: java path class 적용, user/movie/rating 별 test 분리, @Test 붙은 함수에는 parameter 제거.
 * java path class: https://m.blog.naver.com/horajjan/220484659082
 */

/*

//templates.
@RunWith(Parameterized.class)
public class DataReaderTest {
    DataReaderImpl dataReader;
    private String question;
    private String answer;

    //parameter는 테스트 파일, 비교할 결과 파일 2개로 설정.
    @Parameters
    public static Collection<Object[]> testSet(){
        return Arrays.asList(new Object[][]{
                {"testUser1.dat", "resultUser1.dat"},
                {"testMovie1.dat", "resultMovie1.dat"},
                {"testRating1.dat", "resultRating1.dat"},

        });
    }

    public DataReaderTest(String Q, String A) {
        System.out.println("Test case started.");
        this.dataReader = new DataReaderImpl();
        this.question = Q;
        this.answer = A;
    }

 */

    //user,movies,rating 들어왔을 때 각각 data/user,movies,rating.dat 으로 경로 만드는지 확인
    //@Test
    //public void testGetPathFromDataType(){

    //파싱 형태 맞춰서 한 줄의 string 으로 반환하는지 확인.
    //이 부분은 직접 파일 입출력 통해 확인.
    //@Test
    //public void testReadTextFromFile(){


    //UserList 로 변환 하는지 테스트.
    //1 F 1 10 48067-100 5개 로 구성된 테스트 케이스 통과해야 한다.
    //@Test
    //public void testToUserList(String path_userTest){



    //MovieList 로 변환 하는지 테스트.
    //1::A B C D (E F G) (1998)::Animation|Children's|Comedy
    //5개 로 구성된 테스트 케이스 통과해야 한다.
    //@Test
    //public void testToMovieList(String path_movieTest) {



    //RatingList 로 변환 하는지 테스트.
    //1::A B C D (E F G) (1998)::Animation|Children's|Comedy
    //5개 로 구성된 테스트 케이스 통과해야 한다.
    //@Test
    //public void testToRatingList(String path_ratingTest) {



    //parameter 로 파일 경로+이름 받으면
    //user, movie, rating 들어있는지 찾아서
    //해당 유형 return. 유형별 테스트 위함.

    /*
    public DataType testType(String parameter){
        if(parameter.contains("User")){
            return DataType.USER;
        }
        if(parameter.contains("Movie")){
            return DataType.MOVIE;
        }
        if(parameter.contains("Rating")){
            return DataType.RATING;
        }
        return null;
    }
    */



    //parameter 들을 통해 진행.
//}

