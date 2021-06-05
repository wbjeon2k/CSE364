package kr.twww.mrs.controller;

import kr.twww.mrs.controller.object.Error;
import kr.twww.mrs.controller.object.IndexhtmlObject;
import kr.twww.mrs.controller.object.Recommendation;
import kr.twww.mrs.preprocess.Preprocessor;
import kr.twww.mrs.preprocess.object.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
public class IndexhtmlController {
    @Autowired
    private Preprocessor preprocessor;

    @GetMapping("/index.html")
    public IndexhtmlObject homepageReturn (){
        /*
        var allscoreList = preprocessor.getindexhtmlScoreList();
        var top10all = (ArrayList<Recommendation>) allscoreList.subList(0,10).stream()
                .map(
                        score -> new Recommendation(
                                score.movie.title,
                                score.movie.GetGenresText(),
                                score.link.GetURL()
                                , score.poster.getPosterLink()
                        )
                ).collect(Collectors.toList());

         */
        return new IndexhtmlObject();
    }
}
