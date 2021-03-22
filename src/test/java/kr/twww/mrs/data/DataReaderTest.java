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
    DataReaderImpl dataLoader;

    @Before
    public void setUp() throws Exception
    {
        dataLoader = new DataReaderImpl();
    }

    @After
    public void tearDown() throws Exception
    {
        dataLoader = null;
    }

    @Test
    public void getUserList()
    {
        var userList = dataLoader.GetUserList();
        assertNull(userList);
    }

}
*/

/**
 *
 *
 */
@RunWith(Parameterized.class)
public class DataReaderTest {
    DataReaderImpl dataLoader;
    private String question;
    private String answer;

    @Parameters
    public static Collection<Object[]> testSet(){
        return Arrays.asList(new Object[][]{
                {"testUser1.dat", "resultUser1.dat"},
                {"testUser2.dat", "resultUser2.dat"}
        });
    }

    public DataReaderTest(String Q, String A) {
        System.out.println("Test case started.");
        this.dataLoader = new DataReaderImpl();
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


        System.out.print("Printing Userlist\n");

        //원래 dataloader 과 테스트를 어떻게 연결하면 효율적일까?
        var userList = dataLoader.GetUserList();
        for (int i=0; i<userList.size(); ++i) {
            printUser(userList.get(i));
            System.out.print("\n");
        }


        System.out.println("Parameter1: " + question + " Parameter2: " + answer);

        System.out.print("Printing Answer\n");
        //src/test/java/kr/twww/mrs/data/resultUser1.dat
        String testcase_path = "src/test/java/kr/twww/mrs/data/" + question;
        System.out.println("testcase path " + testcase_path + "\n");



        FileInputStream ifstream = null;
        try{
            ifstream = new FileInputStream(testcase_path);
        }
        catch (FileNotFoundException e){
            System.out.println("File not found error!\n");
        }

        Scanner scanner = new Scanner(ifstream);
        for(int i=0;i<5;++i){
            String Line = scanner.nextLine();
            System.out.println((i+1) + "th: " + Line);
        }

        assertTrue(true);
        //assertTrue(true);
    }
}

