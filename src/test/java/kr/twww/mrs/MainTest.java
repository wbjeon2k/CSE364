package kr.twww.mrs;

import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.preprocess.Preprocessor;
import kr.twww.mrs.preprocess.PreprocessorImpl;
import kr.twww.mrs.preprocess.object.Score;
import mockit.Mock;
import mockit.MockUp;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.SpringApplication;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MainTest
{
    @Ignore
    @Test
    public void Test()
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

    @Test
    public void TestMain()
    {
        try
        {
            new Main();

            new MockUp<Preprocessor>() {
                @Mock
                public ArrayList<Score> GetRecommendList( String _gender, String _age, String _occupation )
                {
                    return null;
                }

                @Mock
                public ArrayList<Score> GetRecommendList( String _gender, String _age, String _occupation, String _categories )
                {
                    return null;
                }
            };

            Main.main(new String[] {});
            Main.main(new String[] { "TEST", "TEST", "TEST" });
            Main.main(new String[] { "TEST", "TEST", "TEST", "TEST" });

            new MockUp<PreprocessorImpl>() {
                @Mock
                public ArrayList<Score> GetRecommendList( String _gender, String _age, String _occupation )
                {
                    var result = new ArrayList<Score>();

                    var score = new Score();
                    score.movie = new Movie();
                    score.movie.title = "TEST";
                    score.link = new Link();
                    score.link.imdbId = "1234";

                    result.add(score);

                    return result;
                }

                @Mock
                public ArrayList<Score> GetRecommendList( String _gender, String _age, String _occupation, String _categories )
                {
                    return GetRecommendList(null, null, null);
                }
            };

            Main.main(new String[] { "TEST", "TEST", "TEST", "TEST" });
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail();
        }

        assertTrue(true);
    }
}