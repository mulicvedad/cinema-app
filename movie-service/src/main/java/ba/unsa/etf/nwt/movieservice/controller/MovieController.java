package ba.unsa.etf.nwt.movieservice.controller;

import ba.unsa.etf.nwt.movieservice.model.Movie;
import ba.unsa.etf.nwt.movieservice.model.MovieCreationRequest;
import ba.unsa.etf.nwt.movieservice.model.TmdbResponse;
import ba.unsa.etf.nwt.movieservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/find/title")
    public Movie getMovieByTitle(@RequestParam(value="title") String title) {
        Movie movie = movieService.getMovieByTitle(title);
        if(movie == null) {
            throw new RuntimeException("No movie found!");
        }
        return movie;
    }

    @RequestMapping(value = "/find/id{id}",  method = RequestMethod.GET)
    public Movie getMovieById(@RequestParam(value = "id") String id) {
        return movieService.getMovieById(id);
    }

    @PostMapping("/create")
    public void addNewMovie(@RequestBody MovieCreationRequest request) {
        movieService.addNewMovie(request);
    }

    @GetMapping("/popular")
    public TmdbResponse getMostPopularMovies() {
        return movieService.getPopularMovies();
    }
}
