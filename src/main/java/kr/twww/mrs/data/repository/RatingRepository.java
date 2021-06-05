package kr.twww.mrs.data.repository;

import kr.twww.mrs.data.object.Movie;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.mllib.recommendation.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating, Long> {

    //Movie findBytitle(String Title);
    //Movie findBymovieId(int id);
    //List<Movie> findBygenres(ArrayList<Movie.Genre> genreList);

}
