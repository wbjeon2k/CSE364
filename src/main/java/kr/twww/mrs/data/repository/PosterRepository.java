package kr.twww.mrs.data.repository;

import kr.twww.mrs.data.object.Movie;
import kr.twww.mrs.data.object.Poster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PosterRepository extends MongoRepository<Poster, Integer> {

    //Movie findBytitle(String Title);
    Poster findBymovID(int id);
    //List<Movie> findBygenres(ArrayList<Movie.Genre> genreList);

}