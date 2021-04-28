package kr.twww.mrs;

import kr.twww.mrs.preprocess.Preprocessor;
import kr.twww.mrs.preprocess.PreprocessorImpl;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest
{
    @Ignore
    @Test
    public void Test()
    {
        var tick = System.currentTimeMillis();

        Preprocessor preprocessor = new PreprocessorImpl();
        var testScoreList = preprocessor.GetRecommendList("F", "25", "Grad student", "Action|Comedy");
//        var testScoreList = preprocessor.GetRecommendList("", "", "", "Adventure");

        var elapsedTick = System.currentTimeMillis() - tick;

        System.out.println("Elapsed Tick: " + elapsedTick);
    }
}