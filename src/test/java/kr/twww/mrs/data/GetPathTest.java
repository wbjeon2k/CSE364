package kr.twww.mrs.data;

import org.junit.After;
import org.junit.Before;
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


//UserList 로 변환 하는지 테스트.
//1 F 1 10 48067-100 5개 로 구성된 테스트 케이스 통과해야 한다.
@RunWith(Parameterized.class)
public class GetPathTest {
    DataReaderImpl dataReader;
    private DataType question;
    private Path answer;
    //question: 주어지는 질문
    //answer: 예상되는 답변

    //Path base = Paths.get("data/test_data/");
    static Path users_dat = Paths.get("data/users.dat");
    static Path movies_dat = Paths.get("data/movies.dat");
    static Path ratings_dat = Paths.get("data/ratings.dat");

    //parameter는 테스트 파일, 비교할 결과 파일 2개로 설정.
    //feature-data 참조. 경로 없는경우 null 반환.
    /*
    1~3번째: user,movie,rating 넣었을때 각각 dat 파일에 연결되는지 확인.
    4~ 끝: 적절하지 않은 데이터 들어왔을때 null 반환하는지 확인.
     */
    @Parameters
    public static Collection<Object[]> testSet(){
        return Arrays.asList(new Object[][]{
                {DataType.USER, users_dat},
                {DataType.MOVIE, movies_dat},
                {DataType.RATING, ratings_dat},
                {123, null},
                {true, null},
                {DataType.values(), null}
        });
    }

    public GetPathTest(DataType Q, Path A) {
        System.out.println("GetPathTest: Test case started.");
        this.dataReader = new DataReaderImpl();
        this.question = Q;
        this.answer = A;
    }


    //순차적으로 @Parameters 에 정의된 parameter 넣어서 진행.
    @Test
    public void parameterTest(){
        System.out.println("Parameter test started\n");
        String result = dataReader.GetPathFromDataType(question);
        Path getPath = Paths.get(result);

        Path getPathAbs = getPath.toAbsolutePath();
        Path answerAbs = answer.toAbsolutePath();

        assertEquals(getPathAbs, answerAbs);
    }

    @Before
    public void setUp() throws Exception
    {
        System.out.println("Start GetPathTest!");
        dataReader = new DataReaderImpl();
    }

    @After
    public void tearDown() throws Exception
    {
        System.out.println("Finish GetPathTest!");
        dataReader = null;
    }
}
