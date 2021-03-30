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

    //UserId = 1, gender = female, age = under_18, occupation = K_12_student, zipcode = "48067-100"
    //들어가는 user 객체 만드는  함수.
    static User TCtemplate(){
        User tmp = new User();
        tmp.userId = 1;
        tmp.gender = User.Gender.FEMALE;
        tmp.age = User.Age.UNDER_18;
        tmp.occupation = User.Occupation.K_12_STUDENT;
        tmp.zipCode = "48067-100";
        return tmp;
    }

    //위에서 만든 user 객체 x개 만큼 넣은 ArrayList 만드는 함수.
    static ArrayList<User> tcgen(int x){
        ArrayList<User> ret = new ArrayList<User>();
        for(int i=0;i<x;++i) ret.add(TCtemplate());
        return ret;
    }

    /*
    1번째: User 정보 3개 넣었을때 정상적으로 출력되는지 확인.
    2번째: 잘못된 파일 형식 들어오면 null 반환 하는지 확인.
     */
    @Parameters
    public static Collection<Object[]> testSet(){
        return Arrays.asList(new Object[][]{
                {"", tcgen(0) },
                {"1::F::1::10::48067-100\n1::F::1::10::48067-100\n1::F::1::10::48067-100", tcgen(3)},
//                {"1::F::1::10::48067-100\n1::F::1::10::48067-100 1 F", null},
        });
    }

    public ToUserListTest(String Q, ArrayList<User> A) {
        System.out.println("GetPathTest: Test case started.");
        this.dataReader = new DataReaderImpl();
        this.question = Q;
        this.answer = A;
    }

    public boolean sameUser(User a, User b){
        if(a.userId != b.userId) return false;
        if(a.gender != b.gender) return false;
        if(a.age != b.age) return false;
        if(a.occupation != b.occupation) return false;
        if(!a.zipCode.equals(b.zipCode)) return false;

        return true;
    }

    public boolean compareAnsRes(ArrayList<User> answer, ArrayList<User> result){
        if(answer == null){
            return result == null;
        }

        if(answer.size() == 0){
            return result.size() == 0;
        }

        for (User user : answer) {
//            boolean chk = false;
            for (User value : result) {
//                if (sameUser(user, value)) chk = true;
                if ( !sameUser(user, value) ) return false;
            }
//            if (!chk) return false;
        }

        return true;
    }

    @Test
    public void parameterTest(){
        //question: ReadTextFromFile 을 통해 정상적으로 처리 되었다면 주어질 input
        //answer: ToUserList 가 정상적으로 작동하면 만들 UserList.
        String read_text = question;
        ArrayList<User> result = dataReader.ToUserList(read_text);
        assertTrue(compareAnsRes(answer, result));
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
