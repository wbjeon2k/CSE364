package kr.twww.mrs.controller;

import kr.twww.mrs.controller.object.Recommendation;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.repository.MovieRepository;
import org.apache.spark.sql.catalyst.expressions.Exp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
public class MovieController
{
    @Autowired
    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/movie")
    public ArrayList<Movie> ReturnAllMovie(
    ) throws Exception
    {
        try{
            movieRepository.save(new Movie(1, "testM", "Action"));
            return (ArrayList<Movie>) movieRepository.findAll();
        }
        catch (Exception e){
            throw new Exception("Error at ReturnAllMovie: " + e.getMessage());
        }
    }

}
