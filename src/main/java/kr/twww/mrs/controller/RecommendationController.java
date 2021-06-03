package kr.twww.mrs.controller;

import kr.twww.mrs.controller.object.Recommendation;
import kr.twww.mrs.controller.object.RequestByMovie;
import kr.twww.mrs.controller.object.RequestByUser;
import kr.twww.mrs.preprocess.Preprocessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
public class RecommendationController
{
    @Autowired
    private Preprocessor preprocessor;

    @GetMapping("/users/recommendations.html")
    public ArrayList<Recommendation> Recommend(
            // changed @RequestBody into @RequestParam for HTTP requests
            @RequestParam("gender") String gender,
            @RequestParam("age") String age,
            @RequestParam("occupation") String occupation,
            @RequestParam("genres") String genres
    ) throws Exception
    {
        var result = preprocessor
                .GetRecommendList(
                        gender,
                        age,
                        occupation,
                        genres
                );

        return (ArrayList<Recommendation>)result
                .stream()
                .map(
                        score -> new Recommendation(
                                score.movie.title,
                                score.movie.GetGenresText(),
                                score.link.GetURL()
                        )
                ).collect(Collectors.toList());
    }

    @GetMapping("/movies/recommendations.html")
    public ArrayList<Recommendation> Recommend(
            @RequestParam("title") String title,
            @RequestParam("limit") String limit
    ) throws Exception
    {
        var result = preprocessor
                .GetRecommendList(
                        title,
                        limit
                );

        return (ArrayList<Recommendation>)result
                .stream()
                .map(
                        score -> new Recommendation(
                                score.movie.title,
                                score.movie.GetGenresText(),
                                score.link.GetURL()
                        )
                ).collect(Collectors.toList());
    }
}
