package kr.twww.mrs.preprocess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PreprocessorTest
{
    Preprocessor preprocessor;

    @Before
    public void setUp() throws Exception
    {
        preprocessor = new PreprocessorImpl();
    }

    @After
    public void tearDown() throws Exception
    {
        preprocessor = null;
    }

    /**
     * 아직 구현되지 않아 null을 반환하는 상태
     * 이후에 구현이 되고 테스트가 실패되어야 함
     */
    @Test
    public void getScores()
    {
        var scoreList = preprocessor.GetScores(null, null);
        assertNull(scoreList);
    }

//    /**
//     * 아직 구현되지 않아 null을 반환하는 상태
//     * 이후에 구현이 되고 테스트가 성공되어야 함
//     */
//    @Test
//    public void getScoresWithNull()
//    {
//        var scoreList = preprocessor.GetScores(null, null);
//        assertNotNull(scoreList);
//        assertTrue(scoreList.isEmpty());
//    }
//
//    /**
//     * 아직 구현되지 않아 null을 반환하는 상태
//     * 이후에 구현이 되고 테스트가 성공되어야 함
//     */
//    @Test
//    public void getScoresWithEmpty()
//    {
//        var scoreList = preprocessor.GetScores("", "");
//        assertNotNull(scoreList);
//        assertTrue(scoreList.isEmpty());
//    }
}