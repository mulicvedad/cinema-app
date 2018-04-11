package ba.unsa.etf.nwt.movieservice.controller;

import ba.unsa.etf.nwt.movieservice.model.Review;
import ba.unsa.etf.nwt.movieservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/user/{id}")
    public List<Review> getReviewsByUserId(@PathVariable(value = "id") Long userId) {
        return reviewService.getReviewsByUserId(userId);
    }

    @GetMapping("/movie/{id}")
    public Set<Review> getReviewsByMovie(@PathVariable(value = "id") Long movieId) {
        return reviewService.getReviewsByMovie(movieId);
    }

    @PostMapping
    public void createReview(Review review, Long movieId) {
        reviewService.createReview(review, movieId);
    }
}
