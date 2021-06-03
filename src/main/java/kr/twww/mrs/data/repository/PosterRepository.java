package kr.twww.mrs.data.repository;

import kr.twww.mrs.data.object.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PosterRepository extends MongoRepository<Movie, Integer> {

    //Movie findBytitle(String Title);
    //Movie findBymovieId(int id);
    //List<Movie> findBygenres(ArrayList<Movie.Genre> genreList);

}