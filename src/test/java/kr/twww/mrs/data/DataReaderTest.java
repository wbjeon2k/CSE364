package kr.twww.mrs.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

/***
 * 원래 skeleton
 */

/*
public class DataReaderTest
{
    DataReaderImpldataReader;

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
        var userList =dataReader.GetUserList();
        assertNull(userList);
    }

}
*/

/**
 * Parametrized test 초기 시안.
 * 이런 형태로 다른 테스트들도 진행하면 될지 확인 필요!
 */
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
                {"testUser2.dat", "resultUser2.dat"}
        });
    }

    public DataReaderTest(String Q, String A) {
        System.out.println("Test case started.");
        this.dataReader = new DataReaderImpl();
        this.question = Q;
        this.answer = A;
    }

    void printUser(User u){
        System.out.print(u.userId);
        System.out.print(u.gender);
        System.out.print(u.age);
        System.out.print(u.occupation);
        System.out.print(u.zipCode);
    }

    @Test
    public void parameterTest(){
        System.out.println("Printing Userlist");

        //원래dataReader 과 테스트를 어떻게 연결하면 효율적일까?
        //원래dataReader 의 path 에 어떻게 접근 & 수정?
        //https://github.com/wbjeon2k/CSE364/issues/8#issuecomment-804520940
        //하위 메소드 바로 접근해서 해결 + 개별 하위 메소드 테스트.


        //parameter 제대로 들어갔는지 확인.
        System.out.println("Parameter1: " + question + " Parameter2: " + answer);

        System.out.print("Printing Answer\n");

        //GetUserList() 를 통해 얻은 결과와 비교하는 코드.
        String testcase_path = "src/test/java/kr/twww/mrs/data/" + question;
        System.out.println("testcase path " + testcase_path + "\n");


        //나중에는 resultX.dat 과 userList 비교하여 assert.
        //비교 자동화 하는것이 목표.
        assertTrue(true);
    }
}

