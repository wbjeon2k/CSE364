package kr.twww.mrs.controller;

import kr.twww.mrs.controller.object.Recommendation;
import kr.twww.mrs.controller.object.RequestByMovie;
import kr.twww.mrs.controller.object.RequestByUser;
import kr.twww.mrs.preprocess.Preprocessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
public class RecommendationController
{
    @Autowired
    private Preprocessor preprocessor;

    @GetMapping("/users/recommendations")
    public ArrayList<Recommendation> Recommend(
            @RequestBody RequestByUser requestByUser
    ) throws Exception
    {
        var result = preprocessor.GetRecommendList(
                requestByUser.getGender(),
                requestByUser.getAge(),
                requestByUser.getOccupation(),
                requestByUser.getGenre()
        );

        return (ArrayList<Recommendation>)result.stream().map(
                score -> new Recommendation(
                        score.movie.title,
                        score.movie.GetGenresText(),
                        score.link.GetURL()
                )
        ).collect(Collectors.toList());
    }

    @GetMapping("/movies/recommendations")
    public ArrayList<Recommendation> Recommend(
            @RequestBody RequestByMovie requestByMovie
    ) throws Exception
    {
        var result = preprocessor.GetRecommendList(
                requestByMovie.getTitle(),
                requestByMovie.getLimit()
        );

        return (ArrayList<Recommendation>)result.stream().map(
                score -> new Recommendation(
                        score.movie.title,
                        score.movie.GetGenresText(),
                        score.link.GetURL()
                )
        ).collect(Collectors.toList());
    }
}
