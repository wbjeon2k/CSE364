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
    public void main()
    {
        var tick = System.currentTimeMillis();

        Preprocessor preprocessor = new PreprocessorImpl();
        var testScoreList = preprocessor.GetScoreList("Adventure", "educator");
        var result = Main.CalculateScore(testScoreList);

        var elapsedTick = System.currentTimeMillis() - tick;

        System.out.println(result);
        System.out.println("Elapsed Tick: " + elapsedTick);
    }
}