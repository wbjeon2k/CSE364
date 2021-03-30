package kr.twww.mrs;

import kr.twww.mrs.data.Rating;
import kr.twww.mrs.preprocess.Preprocessor;
import kr.twww.mrs.preprocess.PreprocessorImpl;

import java.util.ArrayList;

public class Main
{
    public static double CalculateScore( ArrayList<Rating> ratingList )
    {
        var sum = 0.0;

        for ( var i : ratingList )
        {
            sum += i.rating;
        }

        var score = sum / ratingList.size();

        return score;
    }

    public static void main( String[] args )
    {
//        System.out.println("Team Woongbae without Woongbae");

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
        var ratingList = preprocessor.GetScoreList(category, occupation);

        // 결과 출력
        var score = CalculateScore(ratingList);
        System.out.println(score);
    }
}
