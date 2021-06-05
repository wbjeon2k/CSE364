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
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
public class IndexhtmlController {
    @Autowired
    private Preprocessor preprocessor;

    @GetMapping("/index.html")
    public IndexhtmlObject homepageReturn () throws Exception {

        //var allscoreList = preprocessor.getindexhtmlScoreList();

        List<Score> allscoreList = new ArrayList<>();
        for(int i=0;i<11;++i)allscoreList.add(new Score());

        var top10tmp = allscoreList.subList(0,10);

        var top10all = (ArrayList<Recommendation>) top10tmp.stream()
                .map(
                        score -> new Recommendation(
                                score.movie.title,
                                score.movie.GetGenresText(),
                                score.link.GetURL()
                                , score.poster.getPosterLink()
                        )
                ).collect(Collectors.toList());


        return new IndexhtmlObject(top10all);
    }
}
