package ba.unsa.etf.nwt.movieservice.service;

import ba.unsa.etf.nwt.movieservice.model.Movie;
import ba.unsa.etf.nwt.movieservice.model.Review;
import ba.unsa.etf.nwt.movieservice.repository.MovieRepository;
import ba.unsa.etf.nwt.movieservice.repository.ReviewRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ReviewService {
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private MovieRepository movieRepository;
    @Autowired @Qualifier("eurekaClient") EurekaClient eurekaClient;
    @Autowired private RestTemplate restTemplate;

    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    public Set<Review> getReviewsByMovie(Long movieId) {
        return movieRepository.getOne(movieId).getReviews();
    }

    public void createReview(Review review, Long movieId) {
        Movie movie = movieRepository.getOne(movieId);
        Application application = eurekaClient.getApplication("user-service");
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "users/" + review.getUserId()
                + "/details";
        try {
            restTemplate.getForEntity(url, Map.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new RuntimeException("User with given ID doesn't exist");
            } else {
                throw new RuntimeException(e.getMessage());
            }
        }
        movie.getReviews().add(review);
        movieRepository.save(movie);
    }
}
