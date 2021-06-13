package kr.twww.mrs.data.repository;

import kr.twww.mrs.data.object.Poster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "poster", path = "poster")
public interface PosterRepository extends MongoRepository<Poster, Long> {

    //Movie findBytitle(String Title);
    //Poster findBymovID(int id);
    //List<Movie> findBygenres(ArrayList<Movie.Genre> genreList);

}