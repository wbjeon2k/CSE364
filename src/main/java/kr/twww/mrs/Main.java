package kr.twww.mrs;

import kr.twww.mrs.preprocess.Preprocessor;
import kr.twww.mrs.preprocess.PreprocessorImpl;

public class Main
{
    public static void main( String[] args )
    {
        System.out.println("Team Woongbae without Woongbae");

        // 인수 2개 검사
        if ( args.length != 2 )
        {
            System.out.println("Error: Invalid argument");
            return;
        }

        // 카테고리, 직업 입력
        var category = args[0];
        var occupation = args[1];

        // 영화 평점 전처리
        Preprocessor preprocessor = new PreprocessorImpl();
        var scoreList = preprocessor.GetScores(category, occupation);

        // 결과 출력
        for ( var i : scoreList )
        {
            System.out.println(i.GetMovie().title + "::" + i.GetScore());
        }
    }
}
