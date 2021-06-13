package kr.twww.mrs.data.repository;

import kr.twww.mrs.data.object.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends MongoRepository<User, Long> {

    //Movie findBytitle(String Title);
    //Movie findBymovieId(int id);
    //List<Movie> findBygenres(ArrayList<Movie.Genre> genreList);

}