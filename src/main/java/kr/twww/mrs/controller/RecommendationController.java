package kr.twww.mrs.controller;

import kr.twww.mrs.controller.object.Recommendation;
import kr.twww.mrs.preprocess.Preprocessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
public class RecommendationController
{
    /*
    @Autowired
    private final MovieRepository movieRepository;
    @Autowired
    public PosterRepository posterRepository;
    @Autowired
    public RatingRepository ratingRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public LinkRepository linkRepository;



    public RecommendationController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
     */

    @Autowired
    private Preprocessor preprocessor;

    @GetMapping("/users/recommendations.html")
    public ArrayList<Recommendation> Recommend(
            // changed @RequestBody into @RequestParam for HTTP requests
            @RequestParam(name = "gender", required = false, defaultValue = "") String gender,
            @RequestParam(name = "age", required = false, defaultValue = "") String age,
            @RequestParam(name = "occupation", required = false, defaultValue = "") String occupation,
            @RequestParam(name = "genres", required = false, defaultValue = "") String genres
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
                                , score.poster.getPosterLink()
                        )
                ).collect(Collectors.toList());
    }

    @GetMapping("/movies/recommendations.html")
    public ArrayList<Recommendation> Recommend(
            @RequestParam(name = "title", required = false, defaultValue = "") String title,
            @RequestParam(name = "limit", required = false, defaultValue = "10") String limits
    ) throws Exception
    {
        var result = preprocessor
                .GetRecommendList(
                        title,
                        limits
                );

        return (ArrayList<Recommendation>)result
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
}
