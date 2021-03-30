package kr.twww.mrs.data;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
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

@RunWith(Parameterized.class)
public class ReadTextFromFileTest {
    DataReaderImpl dataReader;
    Path base = Paths.get("data/test/test_data");
    Path question;
    String answer;
    //parameter 는 테스트 파일, 비교할 결과 파일 2개로 설정.
    //잘못된 입력 들어오면 공백 string 반환.
    @Parameters
    public static Collection<Object[]> testSet(){
        return Arrays.asList(new Object[][]{
                {"testReadText.dat", "TEST::READ\nTEST::READ" },
                {"testReadTextBlank.dat", "" },
                {"NODATA.dat", null},
                {"NoData2.dat", null}
        });
    }

    public ReadTextFromFileTest(String Q, String A) {
        System.out.println("GetPathTest: Test case started.");
        this.dataReader = new DataReaderImpl();
        question = base.resolve(Q);
        answer = A;
    }

    //파싱 형태 맞춰서 한 줄의 string 으로 반환하는지 확인.
    //잘못된 경로 들어오면 ReadTextFromFile 은 공백 string "" return.
    @Test
    public void testReadTextFromFile(){
        String path = question.toString();
        //String result_path = answer.toString();
        System.out.println("testpath: " + path + "expected result: " + answer);

        String testResult = dataReader.ReadTextFromFile(path);

        if ( testResult != null )
        {
            testResult = testResult.replaceAll("\r", "");
        }

        assertEquals(answer, testResult);
    }

    @Before
    public void setUp() throws Exception
    {
        System.out.println("Start ReadTextFromFileTest!");
        dataReader = new DataReaderImpl();
    }

    @After
    public void tearDown() throws Exception
    {
        System.out.println("Finish ReadTextFromFileTest!");
        dataReader = null;
    }
}
