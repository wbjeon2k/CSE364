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

@RunWith(Parameterized.class)
public class ReadTextFromFileTest {
    DataReaderImpl dataReader;
    Path base = Paths.get("data/test/");
    Path question, answer;
    //parameter 는 테스트 파일, 비교할 결과 파일 2개로 설정.

    @Parameters
    public static Collection<Object[]> testSet(){
        return Arrays.asList(new Object[][]{
                {"testReadText.dat", "resultReadText.dat" },
        });
    }

    public ReadTextFromFileTest(String Q, String A) {
        System.out.println("GetPathTest: Test case started.");
        this.dataReader = new DataReaderImpl();
        question = base.resolve(Q);
        answer = base.resolve(A);
    }

    //파싱 형태 맞춰서 한 줄의 string 으로 반환하는지 확인.
    //이 부분은 직접 파일 입출력 통해 확인.
    @Test
    public void testReadTextFromFile(){
        String path = question.toString();
        String result_path = answer.toString();
        System.out.println("testpath: " + path + "result path: " + result_path);

        String testResult = dataReader.ReadTextFromFile(path);

        FileInputStream ifstream;
        try{
            ifstream = new FileInputStream(result_path);
        }
        catch (FileNotFoundException e){
            System.out.println("Test File " + result_path+ " not found");
            return;
        }
        Scanner scanner = new Scanner(ifstream);
        String ans_text = scanner.nextLine();

        assertSame(ans_text, testResult);
    }
}
