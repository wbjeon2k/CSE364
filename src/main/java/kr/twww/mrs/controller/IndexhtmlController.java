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
            System.out.println(allscoreList.size());
        }
        catch (Exception e){
            throw new Exception("Error in  IndexhtmlController homepageReturn getindexhtmlScoreList");
        }


        ArrayList<Score> top10score = new ArrayList<Score>();
        for(int i=0;i<10;++i) top10score.add(allscoreList.get(i));

        ArrayList<Recommendation> ret = new ArrayList<Recommendation>();

        try{
            ret = (ArrayList<Recommendation>)top10score
                    .stream()
                    .map(
                            score -> new Recommendation(
                                    score.movie.title,
                                    score.movie.GetGenresText(),
                                    score.link.GetURL()
                                    , score.poster.getPosterLink()
                            )
                    ).collect(Collectors.toList());
        }
        catch (Exception e){
            throw new Exception("Error in IndexhtmlController homepageReturn objectmapping");
        }


        /*
        for(Score s : top10score){
            String title, genre, imdb, poster;
            try {
                title = s.movie.title;
                genre = s.movie.GetGenresText();
                imdb = s.link.GetURL();
                poster = s.poster.getPosterLink();
            }
            catch (Exception e){
                    throw new Exception("Error in  IndexhtmlController homepageReturn score info");
            }
            try {
                var tmp = new Recommendation(title, genre, imdb, poster);
                ret.add(tmp);
            }
            catch (Exception e){
                throw new Exception("Error in  IndexhtmlController homepageReturn add recommend");
            }
        }
        */
        return ret;
    }



        //return new ArrayList<>();
}

