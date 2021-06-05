package kr.twww.mrs.controller;

import kr.twww.mrs.controller.object.Error;
import kr.twww.mrs.controller.object.IndexhtmlObject;
import kr.twww.mrs.controller.object.Recommendation;
import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.Poster;
import kr.twww.mrs.preprocess.Preprocessor;
import kr.twww.mrs.preprocess.object.Score;
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
            throw new Exception("Error in  IndexhtmlController homepageReturn");
        }


        try{
            var top10score = allscoreList.subList(0,10);
            return (ArrayList<Recommendation>)top10score
                    .stream()
                    .map(
                            score -> new Recommendation(
                                    score.movie.title,
                                    score.movie.GetGenresText(),
                                    score.link.GetURL()
                                    ,score.poster.getPosterLink()
                            )
                    ).collect(Collectors.toList());
        }
        catch (Exception e){
            throw new Exception("Error in  IndexhtmlController homepageReturn");
        }
    }
}
