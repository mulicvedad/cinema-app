package ba.unsa.etf.nwt.movieservice.controller;

import ba.unsa.etf.nwt.movieservice.controller.dto.ReviewDTO;
import ba.unsa.etf.nwt.movieservice.model.ErrorResponseWrapper;
import ba.unsa.etf.nwt.movieservice.model.Error;
import ba.unsa.etf.nwt.movieservice.model.Review;
import ba.unsa.etf.nwt.movieservice.service.MovieService;
import ba.unsa.etf.nwt.movieservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private MovieService movieService;

    @GetMapping("/user/{id}")
    public List<Review> getReviewsByUserId(@PathVariable(value = "id") Long userId) {
        return reviewService.getReviewsByUserId(userId);
    }

    @GetMapping("{id}")
    public Set<Review> getReviewsByMovie(@PathVariable(value = "id") Long movieId) {
        return reviewService.getReviewsByMovie(movieId);
    }

    @PostMapping
    public ResponseEntity createReview(@RequestBody ReviewDTO reviewDTO) {

        if(!movieService.getMovieById(reviewDTO.movieId).isPresent()) {
            Error error = new Error("movieId", "Movie with requested id doesn't exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseWrapper(error));
        }
        reviewService.createReview(reviewDTO);
        return ResponseEntity.ok().build();
    }
}
