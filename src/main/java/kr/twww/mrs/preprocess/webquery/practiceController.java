package kr.twww.mrs.preprocess.webquery;

import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
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
public class practiceController{
    static String blank = "";
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
    ) throws Exception {
        //GetRecommendList 에서 왜 error?
        String gender, age, occupation, genre, title, limit;
        gender = (PQ.getGender() == null ? "" : PQ.getGender());
        age = (PQ.getAge() == null ? "" : PQ.getAge());
        occupation = (PQ.getOccupation() == null ? "" : PQ.getOccupation());
        genre = (PQ.getGenre() == null ? "" : PQ.getGenre());
        title = (PQ.getTitle() == null ? "" : PQ.getTitle());
        limit = (PQ.getLimit() == null ? "" : PQ.getLimit());

        ArrayList<Score> recommend = new ArrayList<>();
        try{
            //recommend = AcquireRecommend(gender,age,occupation,genre,title,limit);
        }
        catch (Exception e){
            throw new Exception("Error: Failed to get recommend\nCause: " + e.getMessage());
        }

        ArrayList<MovieJson> ret = new ArrayList<>();
        for (Score tmp : recommend) {
            ret.add(new MovieJson(tmp));
        }
        return ret;
    }

    public ArrayList<Score> AcquireRecommend(String gender, String age, String occupation, String genre, String title, String limit) throws Exception {

        if(title.compareTo(blank) == 0){
            if(limit.compareTo(blank) != 0) throw new Exception("Invalid input: Limit is not empty");

            if( gender.compareTo(blank) == 0 &&
                age.compareTo(blank) == 0 &&
                occupation.compareTo(blank) == 0 &&
                genre.compareTo(blank) == 0){

                throw new Exception("Invalid input: All gender/age/occupation/genre/title/limit are empty");
            }
            return normalRecommend(gender,age,occupation,genre);
        }
        else{
            if(limit.compareTo(blank) == 0) throw new Exception("Invalid input: Title exist, Limit is empty");
            if( gender.compareTo(blank) != 0 |
                age.compareTo(blank) != 0 |
                occupation.compareTo(blank) != 0 |
                genre.compareTo(blank) != 0){

                throw new Exception("Invalid input: All gender/age/occupation/genre must be empty");
            }
            return titleRecommend(title, limit);
        }

    }

    public ArrayList<Score> normalRecommend(String gender, String age, String occupation, String genre){
        Preprocessor preprocessor = new PreprocessorImpl();
        return preprocessor.GetRecommendList(gender, age, occupation, genre);
    }

    public ArrayList<Score> titleRecommend(String title, String limit){
        Preprocessor preprocessor = new PreprocessorImpl();
        return preprocessor.GetRecommendList(title, limit);
    }
}