package kr.twww.mrs.controller;

import kr.twww.mrs.data.object.Link;
import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.Poster;
import kr.twww.mrs.data.object.User;
import kr.twww.mrs.data.repository.*;
import org.apache.spark.mllib.recommendation.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
public class RepoController
{
    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PosterRepository posterRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/links")
    public ArrayList<Link> ReturnAllLink(
    ) throws Exception
    {
        try{
            return (ArrayList<Link>) linkRepository.findAll();
        }
        catch (Exception e){
            throw new Exception("Error at ReturnAllLink: " + e.getMessage());
        }
    }

    @GetMapping("/movies")
    public ArrayList<Movie> ReturnAllMovie(
    ) throws Exception
    {
        try{
            return (ArrayList<Movie>) movieRepository.findAll();
        }
        catch (Exception e){
            throw new Exception("Error at ReturnAllMovie: " + e.getMessage());
        }
    }

    @GetMapping("/posters")
    public ArrayList<Poster> ReturnAllPoster(
    ) throws Exception
    {
        try{
            return (ArrayList<Poster>) posterRepository.findAll();
        }
        catch (Exception e){
            throw new Exception("Error at ReturnAllPoster: " + e.getMessage());
        }
    }

    @GetMapping("/ratings")
    public ArrayList<Rating> ReturnAllRating(
    ) throws Exception
    {
        try{
            return (ArrayList<Rating>) ratingRepository.findAll();
        }
        catch (Exception e){
            throw new Exception("Error at ReturnAllRating: " + e.getMessage());
        }
    }

    @GetMapping("/users")
    public ArrayList<User> ReturnAllUser(
    ) throws Exception
    {
        try{
            return (ArrayList<User>) userRepository.findAll();
        }
        catch (Exception e){
            throw new Exception("Error at ReturnAllUser: " + e.getMessage());
        }
    }
}
