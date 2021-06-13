package kr.twww.mrs.data.repository;

import kr.twww.mrs.data.object.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "movie", path = "movie")
public interface MovieRepository extends MongoRepository<Movie, Long> {

    //Movie findBytitle(String Title);
    //Movie findBymovieId(int id);
    //List<Movie> findBygenres(ArrayList<Movie.Genre> genreList);

}