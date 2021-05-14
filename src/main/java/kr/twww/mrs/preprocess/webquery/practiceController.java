package kr.twww.mrs.preprocess.webquery;

import kr.twww.mrs.data.object.User;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.preprocess.*;
import kr.twww.mrs.preprocess.object.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class practiceController{
    @GetMapping("/recommendation")
    public practiceQuery getMultiParametersRtnJson(practiceQuery MultiInput){
        //exception 처리

        //gender
        if ( MultiInput.getGender() == null )
        {
            MultiInput.setGender("Error: Invalid gender string");
        }

        if ( MultiInput.getGender().isEmpty() )
        {
            MultiInput.setGender("UNKNOWN");
        }

        if ( MultiInput.getGender().length() != 1 )
        {
            MultiInput.setGender("Error: Invalid gender string");
        }

        //Age
        try
        {
            Integer.parseInt(MultiInput.getAge());
        }
        catch ( Exception e )
        {
            MultiInput.setAge("Error: Invalid age string");
        }

        //occupation(only null and empty now)
        if ( MultiInput.getOccupation() == null )
        {
            MultiInput.setOccupation("Error: Invalid occupation string");
        }

        if ( MultiInput.getOccupation().isEmpty() )
        {
            MultiInput.setOccupation("UNKNOWN");
        }

        //genre
        MultiInput.setGenre(MultiInput.getGenre().replaceAll("[^a-zA-Z0-9]", ""));
        MultiInput.setGenre(MultiInput.getGenre().toUpperCase());

        for ( var i : Movie.Genre.values() )
        {
            var genre = i.name();
            genre = genre.replaceAll("[^a-zA-Z0-9]", "");
            genre = genre.toUpperCase();

            if ( genre.equals(MultiInput.getGenre()) )
            {
                MultiInput.setGenre(genre);
                return MultiInput;
            }
        }
        MultiInput.setGenre("Invalid genre");
        return MultiInput;
    }


    /*
    @GetMapping("/practice")
    public practiceQuery practiceQuery(
            @RequestParam(value = "gender", required = false, defaultValue = "A") String gender,
            @RequestParam(value = "age", required = false,defaultValue = "B") String age,
            @RequestParam(value = "occupation", required = false,defaultValue = "C") String occupation,
            @RequestParam(value = "genre", required = false,defaultValue = "D") String genre
    ){
    */

    /*
    입력은 항상 \ back tick 붙어 있어야 한다...
    | 를 일반 문자로 받지 않아서 문제.
    curl -X GET "http://localhost:8080/practice" -H "Content-type:application/json" -d "{\“gender\” : \“F\”, \“age\” : \“25\”, \“occupation\” : \“Grad student\”, \“genre\” : \“Action|War\”}"
    curl -X GET "http://localhost:8080/practice" -H "Content-type:application/json" -d "{\“title\” : \“Toy Story\”, \“limit\” : \“20\”}"
    */
    //query를 받아서 그대로 출력.
    @GetMapping("/practice")
    public practiceQuery practiceQuery(
            @RequestBody practiceQuery PQ
    ){
        return PQ;
    }

    //curl -X GET "http://localhost:8080/develop" -H "Content-type:application/json" -d "{\“gender\” : \“F\”, \“age\” : \“25\”, \“occupation\” : \“Grad student\”, \“genre\” : \“Action\”}"
    //curl -X GET "http://localhost:8080/develop" -H "Content-type:application/json" -d "{\"gender\" : \"F\", \"age\" : \"25\", \"occupation\" : \"Grad student\", \"genre\" : \"Action\"}"


    //curl -X GET "http://localhost:8080/practice" -H "Content-type:application/json" -d "{\"gender\" : \"F\", \"age\" : \"25\", \"occupation\" : \"Grad student\", \"genre\" : \"Action\"}"
    //curl -X GET "http://localhost:8080/develop" -H "Content-type:application/json" -d "{\"gender\" : \"F\", \"age\" : \"25\", \"occupation\" : \"Grad student\", \"genre\" : \"Action\"}"

    @GetMapping("/develop")
    public ArrayList<MovieJson> normalQuery(
            @RequestBody practiceQuery PQ
    ){
        //GetRecommendList 에서 왜 error?
        String gender, age, occupation, genre;
        gender = PQ.getGender();
        age = PQ.getAge();
        occupation = PQ.getOccupation();
        genre = PQ.getGenre();
        //var result = normalRecommend(gender, age, occupation, genre);

        ArrayList<MovieJson> ret = new ArrayList<>();
        ret.add(new MovieJson());
        ret.add(new MovieJson());
        return ret;
    }

    public ArrayList<Score> normalRecommend(String gender, String age, String occupation, String genre){
        Preprocessor preprocessor = new PreprocessorImpl();
        var result = preprocessor.GetRecommendList(gender, age, occupation, genre);
        return result;
    }
}