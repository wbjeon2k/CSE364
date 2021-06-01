package kr.twww.mrs.data.repository;

import kr.twww.mrs.data.object.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

    Movie findBytitle(String Title);
    Movie findBymovieId(int id);
    List<Movie> findByGenre(Movie.Genre genre);

}