package kr.twww.mrs.controller;

import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.Poster;
import kr.twww.mrs.preprocess.PreprocessorImpl;
import kr.twww.mrs.preprocess.object.Score;
import mockit.Mock;
import mockit.MockUp;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecommendationControllerTest
{
    private MockMvc mvc;

    @Autowired
    private RecommendationController recommendationController;

    @Before
    public void setUp()
    {
        mvc = MockMvcBuilders
                .standaloneSetup(recommendationController)
                .build();
    }

    @Test
    public void TestRecommend() throws Exception
    {
        new MockUp<PreprocessorImpl>() {
            @Mock
            public ArrayList<Score> GetRecommendList(
                    String _gender,
                    String _age,
                    String _occupation,
                    String _categories
            )
            {
                var scoreList = new ArrayList<Score>();

                var score = new Score();
                score.movie = new Movie();
                score.movie.title = "";
                score.movie.genres = new ArrayList<>();
                score.movie.genres.add(Movie.Genre.ACTION);
                score.link = new Link();
                score.link.imdbId = "";

                score.poster = new Poster();
                score.poster.posterLink = "";

                scoreList.add(score);

                return scoreList;
            }
        };

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/users/recommendations.html")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
        ).andExpect(
                MockMvcResultMatchers
                        .status()
                        .isOk()
        );
    }

    @Test
    public void TestRecommend2() throws Exception
    {
        new MockUp<PreprocessorImpl>() {
            @Mock
            public ArrayList<Score> GetRecommendList(
                    String _title,
                    String _limit
            )
            {
                var scoreList = new ArrayList<Score>();

                var score = new Score();
                score.movie = new Movie();
                score.movie.title = "";
                score.movie.genres = new ArrayList<>();
                score.movie.genres.add(Movie.Genre.ACTION);
                score.link = new Link();
                score.link.imdbId = "";

                score.poster = new Poster();
                score.poster.posterLink = "";

                scoreList.add(score);

                return scoreList;
            }
        };

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/movies/recommendations.html")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
        ).andExpect(
                MockMvcResultMatchers
                        .status()
                        .isOk()
        );
    }
}