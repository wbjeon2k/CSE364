package kr.twww.mrs.data.repository;

import org.apache.spark.mllib.recommendation.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "rating", path = "rating")
public interface RatingRepository extends MongoRepository<Rating, Long> {

    //Movie findBytitle(String Title);
    //Movie findBymovieId(int id);
    //List<Movie> findBygenres(ArrayList<Movie.Genre> genreList);

}
