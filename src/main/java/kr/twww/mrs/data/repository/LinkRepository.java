package kr.twww.mrs.data.repository;

import kr.twww.mrs.data.object.Link;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "link", path = "link")
public interface LinkRepository extends MongoRepository<Link, Long> {

    //Movie findBytitle(String Title);
    //Movie findBymovieId(int id);
    //List<Movie> findBygenres(ArrayList<Movie.Genre> genreList);

}