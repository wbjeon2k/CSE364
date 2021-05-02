package kr.twww.mrs.preprocess;

import kr.twww.mrs.data.DataReaderImpl;
import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.User;
import kr.twww.mrs.preprocess.object.Score;
import kr.twww.mrs.preprocess.predict.PredictorImpl;
import mockit.Mock;
import mockit.MockUp;
import org.apache.spark.mllib.recommendation.Rating;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PreprocessorImplTest
{
    private final PreprocessorImpl preprocessor = new PreprocessorImpl();

    @Test
    public void TestGetRecommendList()
    {
        var result = preprocessor.GetRecommendList(
                null,
                null,
                null
        );

        assertNull(result);
    }

    @Test
    public void TestGetCategoryList()
    {
        assertNull(
                preprocessor.GetCategoryList(null)
        );

        var result = preprocessor.GetCategoryList("");
        assertNotNull(result);
        assertTrue(result.isEmpty());

        var result2 = preprocessor.GetCategoryList("action");
        assertNotNull(result2);
        assertEquals(1, result2.size());
        assertEquals(Movie.Genre.ACTION, result2.get(0));

        assertNull(
                preprocessor.GetCategoryList("TEST")
        );
    }

    @Test
    public void TestGetScoreList()
    {
        assertNull(
                preprocessor.GetScoreList(
                        null,
                        null,
                        null,
                        null)
        );

        assertNull(
                preprocessor.GetScoreList(
                        User.Gender.UNKNOWN,
                        null,
                        null,
                        null)
        );

        assertNull(
                preprocessor.GetScoreList(
                        User.Gender.UNKNOWN,
                        User.Age.UNKNOWN,
                        null,
                        null)
        );

        assertNull(
                preprocessor.GetScoreList(
                        User.Gender.UNKNOWN,
                        User.Age.UNKNOWN,
                        User.Occupation.UNKNOWN,
                        null)
        );
    }

    @Test
    public void TestGetScoreList2()
    {
        new MockUp<DataReaderImpl>() {
            @Mock
            public String ReadTextFromFile( String path )
            {
                return "";
            }

            @Mock
            public ArrayList<Rating> GetRatingList()
            {
                return null;
            }
        };

        assertNull(
                preprocessor.GetScoreList(
                        User.Gender.UNKNOWN,
                        User.Age.UNKNOWN,
                        User.Occupation.UNKNOWN,
                        new ArrayList<>())
        );

        new MockUp<DataReaderImpl>() {
            @Mock
            public ArrayList<Movie> GetMovieList()
            {
                return null;
            }
        };

        assertNull(
                preprocessor.GetScoreList(
                        User.Gender.UNKNOWN,
                        User.Age.UNKNOWN,
                        User.Occupation.UNKNOWN,
                        new ArrayList<>())
        );

        new MockUp<DataReaderImpl>() {
            @Mock
            public ArrayList<User> GetUserList()
            {
                return null;
            }
        };

        assertNull(
                preprocessor.GetScoreList(
                        User.Gender.UNKNOWN,
                        User.Age.UNKNOWN,
                        User.Occupation.UNKNOWN,
                        new ArrayList<>())
        );
    }

    @Test
    public void TestGetScoreList3()
    {
        new MockUp<DataReaderImpl>() {
            @Mock
            public ArrayList<User> GetUserList()
            {
                var result = new ArrayList<User>();
                result.add(new User());

                return result;
            }

            @Mock
            public ArrayList<Movie> GetMovieList()
            {
                return new ArrayList<>();
            }

            @Mock
            public ArrayList<Rating> GetRatingList()
            {
                return new ArrayList<>();
            }
        };

        assertNull(
                preprocessor.GetScoreList(
                        User.Gender.UNKNOWN,
                        User.Age.UNKNOWN,
                        User.Occupation.UNKNOWN,
                        new ArrayList<>())
        );

        new MockUp<DataReaderImpl>() {
            @Mock
            public ArrayList<User> GetUserList()
            {
                return new ArrayList<>();
            }

            @Mock
            public ArrayList<Movie> GetMovieList()
            {
                return new ArrayList<>();
            }

            @Mock
            public ArrayList<Rating> GetRatingList()
            {
                return new ArrayList<>();
            }
        };

        new MockUp<PredictorImpl>() {
            @Mock
            boolean LoadModel()
            {
                return false;
            }

            @Mock
            boolean CreateModel( ArrayList<Rating> ratingList )
            {
                return false;
            }
        };

        assertNull(
                preprocessor.GetScoreList(
                        User.Gender.UNKNOWN,
                        User.Age.UNKNOWN,
                        User.Occupation.UNKNOWN,
                        new ArrayList<>())
        );

        new MockUp<PredictorImpl>() {
            @Mock
            boolean LoadModel()
            {
                return false;
            }

            @Mock
            boolean CreateModel( ArrayList<Rating> ratingList )
            {
                return true;
            }

            @Mock
            List<Rating> GetPredictList(
                    List<User> filteredUserList,
                    List<Movie> filteredMovieList
            )
            {
                return null;
            }
        };

        assertNull(
                preprocessor.GetScoreList(
                        User.Gender.UNKNOWN,
                        User.Age.UNKNOWN,
                        User.Occupation.UNKNOWN,
                        new ArrayList<>())
        );

        new MockUp<PredictorImpl>() {
            @Mock
            boolean LoadModel()
            {
                return true;
            }

            @Mock
            boolean CreateModel( ArrayList<Rating> ratingList )
            {
                return true;
            }

            @Mock
            List<Rating> GetPredictList(
                    List<User> filteredUserList,
                    List<Movie> filteredMovieList
            )
            {
                return new ArrayList<>();
            }
        };

        new MockUp<DataReaderImpl>() {
            @Mock
            ArrayList<Link> GetLinkList()
            {
                return null;
            }
        };

        assertNull(
                preprocessor.GetScoreList(
                        User.Gender.UNKNOWN,
                        User.Age.UNKNOWN,
                        User.Occupation.UNKNOWN,
                        new ArrayList<>())
        );
    }

    @Test
    public void TestGetScoreList4()
    {
        new MockUp<DataReaderImpl>() {
            @Mock
            public ArrayList<User> GetUserList()
            {
                return new ArrayList<>();
            }

            @Mock
            public ArrayList<Movie> GetMovieList()
            {
                return new ArrayList<>();
            }

            @Mock
            public ArrayList<Rating> GetRatingList()
            {
                return new ArrayList<>();
            }
        };

        new MockUp<PredictorImpl>() {
            @Mock
            boolean LoadModel()
            {
                return true;
            }

            @Mock
            boolean CreateModel( ArrayList<Rating> ratingList )
            {
                return true;
            }

            @Mock
            public List<Rating> GetPredictList(
                    List<User> filteredUserList,
                    List<Movie> filteredMovieList
            )
            {
                return new ArrayList<>();
            }
        };

        assertNull(
                preprocessor.GetScoreList(
                        User.Gender.UNKNOWN,
                        User.Age.UNKNOWN,
                        User.Occupation.UNKNOWN,
                        new ArrayList<>())
        );

        new MockUp<DataReaderImpl>() {
            @Mock
            public ArrayList<Movie> GetMovieList()
            {
                var result = new ArrayList<Movie>();

                var movie = new Movie();
                movie.movieId = 1;
                result.add(movie);

                return result;
            }

            @Mock
            public ArrayList<Rating> GetRatingList()
            {
                var result = new ArrayList<Rating>();

                var rating = new Rating(1, 1, 0.0);

                for ( var i = 0; i < 10; ++i )
                {
                    result.add(rating);
                }

                return result;
            }
        };

        new MockUp<PredictorImpl>() {
            @Mock
            public List<Rating> GetPredictList(
                    List<User> filteredUserList,
                    List<Movie> filteredMovieList
            )
            {
                var result = new ArrayList<Rating>();
                result.add(
                        new Rating(0, 0, 0.0)
                );

                return result;
            }
        };

        assertNotNull(
                preprocessor.GetScoreList(
                        User.Gender.UNKNOWN,
                        User.Age.UNKNOWN,
                        User.Occupation.UNKNOWN,
                        new ArrayList<>())
        );

        new MockUp<DataReaderImpl>() {
            @Mock
            public ArrayList<Link> GetLinkList()
            {
                return null;
            }
        };

        assertNull(
                preprocessor.GetScoreList(
                        User.Gender.UNKNOWN,
                        User.Age.UNKNOWN,
                        User.Occupation.UNKNOWN,
                        new ArrayList<>())
        );
    }

    @Test
    public void TestGetScoreList5()
    {
        var genreList = new ArrayList<Movie.Genre>();
        genreList.add(Movie.Genre.ACTION);
        genreList.add(Movie.Genre.COMEDY);

        var result = preprocessor.GetScoreList(
                User.Gender.FEMALE,
                User.Age.BETWEEN_25_34,
                User.Occupation.COLLEGE_OR_GRAD_STUDENT,
                genreList);

        assertNotNull(result);
        assertEquals(10, result.size());
        assertTrue(result.get(0).score >= result.get(1).score);
        assertTrue(result.get(1).score >= result.get(2).score);
        assertTrue(result.get(2).score >= result.get(3).score);
        assertTrue(result.get(3).score >= result.get(4).score);
        assertTrue(result.get(4).score >= result.get(5).score);
        assertTrue(result.get(5).score >= result.get(6).score);
        assertTrue(result.get(6).score >= result.get(7).score);
        assertTrue(result.get(7).score >= result.get(8).score);
        assertTrue(result.get(8).score >= result.get(9).score);
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
                            filteredMovieList)
            );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
                            ratingList)
            );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail();
        }
    }
}