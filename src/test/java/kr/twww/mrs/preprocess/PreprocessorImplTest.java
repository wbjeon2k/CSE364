package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.User;
import kr.twww.mrs.preprocess.object.Score;
import kr.twww.mrs.preprocess.predict.PredictorImpl;
import mockit.Mock;
import mockit.MockUp;
import org.apache.spark.mllib.recommendation.Rating;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PreprocessorImplTest
{
    @Autowired
    private PreprocessorImpl preprocessor;

    @Test
    public void TestGetRecommendList() throws Exception
    {
        System.out.println("TestGetRecommendList start");
        try
        {
            System.out.println("TestGetRecommendList 1 start");
            preprocessor.GetRecommendList(
                    null,
                    null,
                    null
            );

            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        new MockUp<PreprocessorImpl>() {
            @Mock
            public ArrayList<Score> GetScoreListByUser(
                    User.Gender gender,
                    User.Age age,
                    User.Occupation occupation,
                    ArrayList<Movie.Genre> genreList
            )
            {
                return new ArrayList<>();
            }
        };

        var result = preprocessor.GetRecommendList(
                "",
                "",
                ""
        );

        System.out.println("TestGetRecommendList 2 start");
        assertNotNull(result);
        assertTrue(result.isEmpty());

        new MockUp<PreprocessorImpl>() {
            @Mock
            public ArrayList<Score> GetScoreListByMovie(
                    Movie movie,
                    int limit
            )
            {
                return new ArrayList<>();
            }
        };

        var result2 = preprocessor.GetRecommendList(
                "toystory(1995)",
                "10"
        );

        System.out.println("TestGetRecommendList 3 start");

        assertNotNull(result2);
        assertTrue(result2.isEmpty());
    }

    @Test
    public void TestGetCategoryList() throws Exception
    {
        System.out.println("TestGetCategoryList start");
        try
        {
            System.out.println("TestGetCategoryList 1 start");
            preprocessor.GetCategoryList(
                    null
            );
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        var result = preprocessor.GetCategoryList(
                ""
        );
        System.out.println("TestGetCategoryList 2 start");
        assertNotNull(result);
        assertTrue(result.isEmpty());

        var result2 = preprocessor.GetCategoryList(
                "action"
        );
        System.out.println("TestGetCategoryList 3 start");
        assertNotNull(result2);
        assertEquals(1, result2.size());
        assertEquals(Movie.Genre.ACTION, result2.get(0));

        try
        {
            System.out.println("TestGetCategoryList 4 start");
            preprocessor.GetCategoryList("TEST");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }
    }

    @Test
    public void TestGetMovieFromTitle() throws Exception
    {
        try
        {
            preprocessor.GetMovieFromTitle(null);
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        try
        {
            preprocessor.GetMovieFromTitle("");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        var result = preprocessor.GetMovieFromTitle("toystory(1995)");

        assertNotNull(result);
        assertEquals(
                "Toy Story (1995)",
                result.title
        );
    }

    @Test
    public void TestConvertLimit() throws Exception
    {
        try
        {
            preprocessor.ConvertLimit(null);
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        try
        {
            preprocessor.ConvertLimit("one");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        try
        {
            preprocessor.ConvertLimit("-1");
            fail();
        }
        catch ( Exception exception )
        {
            assertTrue(true);
        }

        assertEquals(
                123,
                preprocessor.ConvertLimit("123")
        );
    }

    @Test
    public void TestGetScoreListByUser() throws Exception
    {
        System.out.println("TestGetScoreListByUser start");
        var genreList = new ArrayList<Movie.Genre>();
        genreList.add(Movie.Genre.ACTION);
        genreList.add(Movie.Genre.COMEDY);

        var result = preprocessor.GetScoreListByUser(
                User.Gender.FEMALE,
                User.Age.BETWEEN_25_34,
                User.Occupation.COLLEGE_OR_GRAD_STUDENT,
                genreList
        );

        assertNotNull(result);
        assertEquals(10, result.size());

        for ( var i = 0; i < 9; ++i )
        {
            assertTrue(
                    result.get(i).score
                            >= result.get(i + 1).score
            );
        }
        System.out.println("TestGetScoreListByUser end");
    }

    @Test
    public void TestGetScoreListByMovie() throws Exception
    {
        System.out.println("TestGetScoreListByMovie start");
        var movie = new Movie();
        movie.movieId = 1;
        movie.title = "Toy Story (1995)";
        movie.genres = new ArrayList<>();
        movie.genres.add(Movie.Genre.ANIMATION);
        movie.genres.add(Movie.Genre.CHILDREN_S);
        movie.genres.add(Movie.Genre.COMEDY);

        var result = preprocessor.GetScoreListByMovie(
                movie,
                10
        );

        assertNotNull(result);
        assertEquals(10, result.size());

        result.forEach(
                score -> assertNotEquals(
                    movie.movieId,
                    score.movie.movieId
                )
        );
        System.out.println("TestGetScoreListByMovie end");
    }

    @Test
    public void TestSelectFilteredUser()
    {
        try
        {
            var method = PreprocessorImpl
                    .class
                    .getDeclaredMethod("SelectFilteredUser", List.class, List.class, List.class);
            method.setAccessible(true);

            var ratingList = new ArrayList<Rating>();
            ratingList.add(new Rating(1, 0, 0.0));
            ratingList.add(new Rating(2, 0, 0.0));

            var filteredUserList = new ArrayList<User>();
            var user = new User();
            user.userId = 1;
            filteredUserList.add(user);
            filteredUserList.add(user);

            var filteredMovieList = new ArrayList<Movie>();
            var movie = new Movie();

            var field = PreprocessorImpl
                    .class
                    .getDeclaredField("MAX_PAIR_COUNT");
            field.setAccessible(true);

            var max = (int)field.get(preprocessor);

            for ( var i = 0; i < max; ++i )
            {
                filteredMovieList.add(movie);
            }

            assertNotNull(
                    method.invoke(
                            preprocessor,
                            ratingList,
                            filteredUserList,
                            filteredMovieList
                    )
            );
        }
        catch ( Exception e )
        {
            fail();
        }
    }

    @Test
    public void TestSelectPredict()
    {
        try
        {
            var method = PreprocessorImpl
                    .class
                    .getDeclaredMethod("SelectPredict", List.class, List.class);
            method.setAccessible(true);

            var predictList = new ArrayList<Rating>();
            predictList.add(new Rating(1, 0, 0.0));
            predictList.add(new Rating(2, 0, 0.0));

            var filteredMovieList = new ArrayList<Movie>();
            filteredMovieList.add(new Movie());

            assertEquals(
                    predictList,
                    method.invoke(
                            preprocessor,
                            predictList,
                            filteredMovieList
                    )
            );
        }
        catch ( Exception e )
        {
            fail();
        }
    }

    @Test
    public void TestGetFilteredUserList()
    {
        try
        {
            var method = PreprocessorImpl
                    .class
                    .getDeclaredMethod("GetFilteredUserList", User.Gender.class, User.Age.class, User.Occupation.class, List.class);
            method.setAccessible(true);

            var userList = new ArrayList<>();
            userList.add(new User());

            assertNotNull(
                    method.invoke(
                            preprocessor,
                            User.Gender.UNKNOWN,
                            User.Age.UNKNOWN,
                            User.Occupation.UNKNOWN,
                            userList
                    )
            );

            assertNotNull(
                    method.invoke(
                            preprocessor,
                            User.Gender.UNKNOWN,
                            User.Age.UNKNOWN,
                            null,
                            userList
                    )
            );

            assertNotNull(
                    method.invoke(
                            preprocessor,
                            User.Gender.UNKNOWN,
                            null,
                            User.Occupation.UNKNOWN,
                            userList
                    )
            );

            assertNotNull(
                    method.invoke(
                            preprocessor,
                            null,
                            User.Age.UNKNOWN,
                            User.Occupation.UNKNOWN,
                            userList
                    )
            );

            assertNotNull(
                    method.invoke(
                            preprocessor,
                            User.Gender.UNKNOWN,
                            null,
                            null,
                            userList
                    )
            );

            assertNotNull(
                    method.invoke(
                            preprocessor,
                            null,
                            User.Age.UNKNOWN,
                            null,
                            userList
                    )
            );

            assertNotNull(
                    method.invoke(
                            preprocessor,
                            null,
                            null,
                            User.Occupation.UNKNOWN,
                            userList
                    )
            );
        }
        catch ( Exception e )
        {
            fail();
        }
    }

    @Test
    public void TestGetFilteredMovieList()
    {
        try
        {
            var method = PreprocessorImpl
                    .class
                    .getDeclaredMethod("GetFilteredMovieList", List.class, List.class, List.class);
            method.setAccessible(true);

            var ratingList = new ArrayList<Rating>();
            for ( var i = 0; i < 10; ++i )
            {
                ratingList.add(new Rating(0, 1, 0.0));
            }
            ratingList.add(new Rating(0, 2, 0.0));

            var filteredMovieList = new ArrayList<Movie>();
            var movie = new Movie();
            movie.movieId = 1;
            filteredMovieList.add(movie);
            filteredMovieList.add(movie);

            assertNotNull(
                    method.invoke(
                            preprocessor,
                            new ArrayList<>(),
                            filteredMovieList,
                            ratingList
                    )
            );

            try
            {
                method.invoke(
                        preprocessor,
                        new ArrayList<>(),
                        filteredMovieList,
                        new ArrayList<>()
                );
                fail();
            }
            catch ( Exception exception )
            {
                assertTrue(true);
            }
        }
        catch ( Exception e )
        {
            fail();
        }
    }

    @Test
    public void TestGetFilteredUserStream()
    {
        try
        {
            var method = PreprocessorImpl
                    .class
                    .getDeclaredMethod("GetFilteredUserStream", User.Gender.class, User.Age.class, User.Occupation.class, List.class, int.class);
            method.setAccessible(true);

            assertNotNull(
                    method.invoke(
                            preprocessor,
                            User.Gender.UNKNOWN,
                            User.Age.UNKNOWN,
                            User.Occupation.UNKNOWN,
                            new ArrayList<>(),
                            3
                    )
            );
        }
        catch ( Exception e )
        {
            fail();
        }
    }

    @Test
    public void TestGetPredictList()
    {
        new MockUp<PredictorImpl>() {
            @Mock
            public boolean LoadModel()
            {
                return false;
            }

            @Mock
            public boolean CreateModel(
                    ArrayList<Rating> ratingList
            )
            {
                return false;
            }
        };

        try
        {
            var method = PreprocessorImpl
                    .class
                    .getDeclaredMethod("GetPredictList", List.class, List.class, ArrayList.class);
            method.setAccessible(true);

            assertNotNull(
                    method.invoke(
                            preprocessor,
                            null,
                            null,
                            null
                    )
            );
            fail();
        }
        catch ( Exception e )
        {
            assertTrue(true);
        }
    }

    @Test
    public void TestSortingTopList()
    {
        try
        {
            var method = PreprocessorImpl
                    .class
                    .getDeclaredMethod("SortingTopList", ArrayList.class);
            method.setAccessible(true);

            assertNotNull(
                    method.invoke(
                            preprocessor,
                            new ArrayList<>()
                    )
            );
        }
        catch ( Exception e )
        {
            fail();
        }
    }
}