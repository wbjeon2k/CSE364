package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.*;
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
public class GetOccupationTest {
    PreprocessorImpl dataPreprocessor;
    String question;
    User.Occupation answer;



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
        User.Occupation result = dataPreprocessor.GetOccupation(question);
        assertEquals(result, answer);
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