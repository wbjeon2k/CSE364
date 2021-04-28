package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.object.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;



@Ignore
@RunWith(Parameterized.class)
public class GetOccupationTest {
    PreprocessorImpl dataPreprocessor;
    String question;
    User.Occupation answer;

    //현재는 grad student 만 확인함.
    //나머지는 추가 예정.
    @Parameters
    public static Collection<Object[]> testSet() {
        return Arrays.asList(new Object[][]{
                {"gradstudent", User.Occupation.COLLEGE_OR_GRAD_STUDENT},
                {"grad_student", User.Occupation.COLLEGE_OR_GRAD_STUDENT},
                {"grad student", User.Occupation.COLLEGE_OR_GRAD_STUDENT},
                {"gra,", null}
        });
    }

    public GetOccupationTest(String Q, User.Occupation A){
        this.dataPreprocessor = new PreprocessorImpl();
        this.question = Q;
        this.answer = A;
    }

    @Test
    public void parameterTest(){
//        User.Occupation result = dataPreprocessor.GetOccupation(question);
//        assertEquals(result, answer);
    }

    @Before
    public void setUp() throws Exception
    {
        System.out.println("Starting GetOccupation test!");
        dataPreprocessor = new PreprocessorImpl();
    }

    @After
    public void tearDown() throws Exception
    {
        System.out.println("Finishing GetOccupation test!");
        dataPreprocessor = null;
    }
}