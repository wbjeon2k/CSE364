package kr.twww.mrs.controller;

import kr.twww.mrs.controller.object.Error;
import kr.twww.mrs.controller.object.IndexhtmlObject;
import kr.twww.mrs.controller.object.Recommendation;
import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.Poster;
import kr.twww.mrs.preprocess.Preprocessor;
import kr.twww.mrs.preprocess.object.Score;
import org.apache.spark.internal.config.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
public class IndexhtmlController {
    @Autowired
    private Preprocessor preprocessor;

    @GetMapping("/index.html")
    public ArrayList<Recommendation> homepageReturn () throws Exception {
        ArrayList<Score> allscoreList;
        try{
            allscoreList = preprocessor.getindexhtmlScoreList();
        }
        catch (Exception e){
            throw new Exception("Error in  IndexhtmlController homepageReturn getindexhtmlScoreList");
        }


        try{
            var top10score = allscoreList.subList(0,10);
            var ret = new ArrayList<Recommendation>();
            for(Score s : top10score){
                //private String title;String genre;String imdb;String poster;
                var tmp = new Recommendation(s.movie.title, s.movie.GetGenresText(), s.link.GetURL(), s.poster.getPosterLink());
                ret.add(tmp);
            }
            return ret;
        }
        catch (Exception e){
            throw new Exception("Error in  IndexhtmlController homepageReturn scorelistextract");
        }

        //return new ArrayList<>();
    }
}
