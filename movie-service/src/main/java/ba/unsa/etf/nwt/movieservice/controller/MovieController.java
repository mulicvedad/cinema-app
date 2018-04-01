package ba.unsa.etf.nwt.movieservice.controller;

import ba.unsa.etf.nwt.movieservice.model.Movie;
import ba.unsa.etf.nwt.movieservice.model.MovieCreationRequest;
import ba.unsa.etf.nwt.movieservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/find")
    public Movie getMovieByTitle(@RequestParam(value="title") String title) {
        Movie movie = movieService.getMovieByTitle(title);
        if(movie == null) {
            throw new RuntimeException("No movie found!");
        }
        return movie;
    }

    @PostMapping("/create")
    public void addNewMovie(@RequestBody MovieCreationRequest request) {
        movieService.addNewMovie(request);
    }
}
