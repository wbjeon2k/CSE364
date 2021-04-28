package kr.twww.mrs;

import kr.twww.mrs.preprocess.Preprocessor;
import kr.twww.mrs.preprocess.PreprocessorImpl;

public class Main
{
    public static void main( String[] args )
    {
        if ( !(args.length == 3 || args.length == 4) )
        {
            System.out.println("Error: Invalid argument");
            return;
        }

        var gender = args[0];
        var age = args[1];
        var occupation = args[2];
        var categories = args.length == 4 ? args[3] : "";

        Preprocessor preprocessor = new PreprocessorImpl();
        var result = preprocessor.GetRecommendList(gender, age, occupation, categories);

        if ( result == null )
        {
            System.out.println("Error");
            return;
        }

        for ( var i : result )
        {
            System.out.println(i.movie.title + " (" + i.link.GetURL() + ")");
        }
    }
}
