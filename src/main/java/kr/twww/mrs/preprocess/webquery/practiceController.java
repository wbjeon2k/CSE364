package kr.twww.mrs.preprocess.webquery;

import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.preprocess.*;
//import kr.twww.mrs.preprocess.Preprocessor;
//import kr.twww.mrs.preprocess.PreprocessorImpl;
import kr.twww.mrs.preprocess.object.Score;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class practiceController{

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
    @GetMapping("/develop")
    public ArrayList<MovieJson> normalQuery(
            @RequestBody practiceQuery PQ
    ){
        //GetRecommendList 에서 왜 error?
        var result = normalRecommend(PQ.getGender(), PQ.getAge(), PQ.getOccupation(), PQ.getGenre());
        ArrayList<MovieJson> ret = new ArrayList<>();
        ret.add(new MovieJson());
        ret.add(new MovieJson());
        return ret;
    }

    public ArrayList<Score> normalRecommend(String gender, String age, String occupation, String genre){
        Preprocessor preprocessor = new PreprocessorImpl();
        return preprocessor.GetRecommendList(gender, age, occupation, genre);
    }
}