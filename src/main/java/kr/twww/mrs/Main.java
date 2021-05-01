package kr.twww.mrs;

import kr.twww.mrs.preprocess.Preprocessor;
import kr.twww.mrs.preprocess.PreprocessorImpl;
import org.apache.commons.codec.binary.StringUtils;

public class Main
{
    public static void main( String[] args )
    {
        if ( !(args.length == 3 || args.length == 4) )
        {
            System.out.println("Error: Invalid number of arguments");
            return;
        }

        var gender = args[0];
        var age = args[1];
        var occupation = args[2];
        var categories = args.length == 4 ? args[3] : "";

        System.out.println("[Group 4] Movie Recommendation System");

        Preprocessor preprocessor = new PreprocessorImpl();
        var result = preprocessor.GetRecommendList(gender, age, occupation, categories);

        if ( result == null )
        {
            System.out.println("Error: Cannot recommendation failed");
            return;
        }

        System.out.println("Info: Movie recommendation:");

        var count = 0;

        for ( var i : result )
        {
            ++count;

            System.out.println(count + ". " + i.movie.title + " (" + i.link.GetURL() + ")");
        }
    }
}
