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

@RunWith(Parameterized.class)
public class ToUserListTest {
    DataReaderImpl dataReader;
    String original_parameter1, original_parameter2;
    Path base = Paths.get("data/test/");
    Path question, answer;

    @Parameters
    public static Collection<Object[]> testSet(){
        return Arrays.asList(new Object[][]{
                {"testUser1.dat", "resultUser1.dat" },
                {"error_path1.dat", "" },
                {"error_data_user1.dat", "" },
        });
    }

    public ToUserListTest(String Q, String A) {
        System.out.println("GetPathTest: Test case started.");
        this.dataReader = new DataReaderImpl();
        question = base.resolve(Q);
        answer = base.resolve(A);
        original_parameter1 = Q;
        original_parameter2 = A;
    }

    @Test
    public void error_path_Test(){
        String q = question.toString();
        if(!q.contains("error_path")) return;

        String read_text = dataReader.ReadTextFromFile(question.toString());
        ArrayList<User> result = dataReader.ToUserList(read_text);
        assertNull(result);
    }

    @Test
    public void error_data_Test(){
        String q = question.toString();
        if(!q.contains("error_data")) return;

        String read_text = dataReader.ReadTextFromFile(question.toString());
        ArrayList<User> result = dataReader.ToUserList(read_text);
        assertNull(result);
    }

    @Test
    public void parameterTest(){
        String q = question.toString();
        if(!q.contains("test")) return;

        String read_text = dataReader.ReadTextFromFile(question.toString());
        ArrayList<User> result = dataReader.ToUserList(read_text);

        assertEquals(result.size(), 5);

        /*
        public int userId;
        public Gender gender;
        public Age age;
        public Occupation occupation;
        public int zipCode;
        //1 F 1 10 48067-100 5개.
         */
        for(int i=0; i< result.size();++i){
            User now = result.get(i);
            assertEquals(now.userId, 1);
            assertEquals(now.gender, User.Gender.FEMALE);
            assertEquals(now.age, User.Age.UNDER_18);
            assertEquals(now.occupation, User.Occupation.K_12_STUDENT);
            //**zipcode 자료형 String**!
            assertEquals( now.zipCode, "48067-100");
            //assertEquals( Integer.toString(now.zipCode), "48067-100");
            //1 F 1 10 48067-100
        }
    }

    @Before
    public void setUp() throws Exception
    {
        System.out.println("Starting ToUserList test!");
        dataReader = new DataReaderImpl();
    }

    @After
    public void tearDown() throws Exception
    {
        System.out.println("Finishing ToUserList test!");
        dataReader = null;
    }

}
