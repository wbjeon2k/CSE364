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
    Path base = Paths.get("data/");
    static Path users_dat = Paths.get("data/users.dat");
    static Path movies_dat = Paths.get("data/movies.dat");
    static Path ratings_dat = Paths.get("data/ratings.dat");


    //parameter는 테스트 파일, 비교할 결과 파일 2개로 설정.
    @Parameters
    public static Collection<Object[]> testSet(){
        return Arrays.asList(new Object[][]{
                {DataType.USER, users_dat},
                {DataType.MOVIE, movies_dat},
                {DataType.RATING, ratings_dat},
        });
    }

    public GetPathTest(DataType Q, Path A) {
        System.out.println("GetPathTest: Test case started.");
        this.dataReader = new DataReaderImpl();
        this.question = Q;
        this.answer = A;
    }

    @Test
    public void parameterTest(){
        System.out.println("Parameter test started\n");
        String result = dataReader.GetPathFromDataType(question);
        Path getPath = Paths.get(result);
        assertEquals(getPath, answer);
    }

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
}
