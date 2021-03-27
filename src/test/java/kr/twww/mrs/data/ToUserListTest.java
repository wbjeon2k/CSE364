package kr.twww.mrs.data;

import kr.twww.mrs.*;

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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class ToUserListTest {
    DataReaderImpl dataReader;
    String question;
    ArrayList<User> answer;

    static User TCtemplate(){
        User tmp = new User();
        tmp.userId = 1;
        tmp.gender = User.Gender.FEMALE;
        tmp.age = User.Age.UNDER_18;
        tmp.occupation = User.Occupation.K_12_STUDENT;
        tmp.zipCode = "48067-100";
        return tmp;
    }

    static ArrayList<User> tcgen(int x){
        ArrayList<User> ret = new ArrayList<User>();
        for(int i=0;i<x;++i) ret.add(TCtemplate());
        return ret;
    }

    @Parameters
    public static Collection<Object[]> testSet(){
        return Arrays.asList(new Object[][]{
                {"", tcgen(0) },
                {"1::F::1::10::48067-100 1::F::1::10::48067-100 1::F::1::10::48067-100", tcgen(3)},
                {"1::F::1::10::48067-100 1::F::1::10::48067-100 1 F", null},
        });
    }

    public ToUserListTest(String Q, ArrayList<User> A) {
        System.out.println("GetPathTest: Test case started.");
        this.dataReader = new DataReaderImpl();
        this.question = Q;
        this.answer = A;
    }

    @Test
    public void parameterTest(){
        //question: ReadTextFromFile 을 통해 정상적으로 처리 되었다면 주어질 input
        //answer: ToUserList 가 정상적으로 작동하면 만들 UserList.
        String read_text = question;
        ArrayList<User> result = dataReader.ToUserList(read_text);
        assertThat(result, is(answer));
    }
    /*
    public int userId;
    public Gender gender;
    public Age age;
    public Occupation occupation;
    public int zipCode;
    //1 F 1 10 48067-100 5개.
     */

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
