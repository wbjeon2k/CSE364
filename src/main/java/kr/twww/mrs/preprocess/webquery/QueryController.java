package kr.twww.mrs.preprocess.webquery;

import kr.twww.mrs.preprocess.*;
import kr.twww.mrs.preprocess.object.Score;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class QueryController {
    /*
    입력은 항상 \ back tick 붙어 있어야 한다. | 를 일반 문자로 받지 않아서 문제.
    //curl -X GET "http://localhost:8080/practice" -H "Content-type:application/json" -d "{\"gender\" : \"F\", \"age\" : \"25\", \"occupation\" : \"Grad student\", \"genre\" : \"Action\"}"
    */
    //query를 받아서 그대로 출력.
    @GetMapping("/practice")
    public QueryResource practiceQuery(
            @RequestBody QueryResource PQ
    ){
        return PQ;
    }

    //curl -X GET "http://localhost:8080/develop" -H "Content-type:application/json" -d "{\"gender\" : \"F\", \"age\" : \"25\", \"occupation\" : \"Grad student\", \"genre\" : \"Action\"}"
    //curl -X GET "http://localhost:8080/develop" -H "Content-type:application/json" -d "{\"title\" : \"Toy Story\", \"limit\" : \"20\"}"

    //같은 url에 여러개 controller method 들 overload/mapping 불가능. 아래 링크 참조.
    //https://stackoverflow.com/questions/34587254/accessing-multiple-controllers-with-same-request-mapping
    @GetMapping("/develop")
    public ArrayList<ScoreToJson> normalQuery(
            @RequestBody QueryResource NQ
    ) throws Exception {
        String gender, age, occupation, genre, title, limit;
        gender = NQ.getGender();
        age = NQ.getAge();
        occupation = NQ.getOccupation();
        genre = NQ.getGenre();
        title = NQ.getTitle();
        limit = NQ.getLimit();

        ArrayList<Score> recommend;
        try{
            recommend = acquireRecommend(gender, age, occupation, genre, title, limit);
        }
        catch (Exception e){
            throw new Exception("Error: Failed to get recommend\nCause: " + e.getMessage());
        }

        ArrayList<ScoreToJson> ret = new ArrayList<>();
        for (Score tmp : recommend) {
            ret.add(new ScoreToJson(tmp));
        }
        return ret;
    }

    //logic 이 조금 길어서 별도 method 분리.
    public ArrayList<Score> acquireRecommend(
            String gender, String age, String occupation,
            String genre, String title, String limit
    ) throws Exception {
        Preprocessor P = new PreprocessorImpl();

        if(title != null){
            if(limit == null) throw new Exception("Error: parameter limit is null");

            if(gender != null | age != null | occupation != null | genre != null)
                throw new Exception("Error: Title exist, but gender/age/occupation/genre exist");

            return P.GetRecommendList(title, limit);
        }
        else{
            if(limit != null) throw new Exception("Error: Title is null, but limit is not null");

            if(gender == null | age == null | occupation == null | genre == null)
                throw new Exception("Error: All gender/age/occupation/genre should exist");

            return P.GetRecommendList(gender,age,occupation,genre);
        }
    }

}