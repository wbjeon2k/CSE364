package kr.twww.mrs;

import kr.twww.mrs.preprocess.Preprocessor;
import kr.twww.mrs.preprocess.PreprocessorImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class MainTest
{
    @Test
    public void Test() throws Exception
    {
        var tick = System.currentTimeMillis();

        Preprocessor preprocessor = new PreprocessorImpl();
//        var testScoreList = preprocessor.GetRecommendList("F", "10", "", "");
//        var testScoreList = preprocessor.GetRecommendList("F", "25", "Grad student", "Action|Comedy");
        var testScoreList = preprocessor.GetRecommendList("", "", "");
//        var testScoreList = preprocessor.GetRecommendList("", "", "", "adventure");

        var elapsedTick = System.currentTimeMillis() - tick;

        for ( var i : testScoreList )
        {
            System.out.println(i.movie.title + " (" + i.link.GetURL() + ")");
        }

        System.out.println("Elapsed Tick: " + elapsedTick);
    }
}