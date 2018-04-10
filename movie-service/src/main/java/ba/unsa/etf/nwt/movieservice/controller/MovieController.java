package ba.unsa.etf.nwt.movieservice.controller;

import ba.unsa.etf.nwt.movieservice.model.*;
import ba.unsa.etf.nwt.movieservice.model.Error;
import ba.unsa.etf.nwt.movieservice.model.Movie;
import ba.unsa.etf.nwt.movieservice.model.MovieCreationRequest;
import ba.unsa.etf.nwt.movieservice.service.MovieService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/create")
    public void addNewMovie(@RequestBody MovieCreationRequest request) {
        movieService.addNewMovie(request);
    }

    @GetMapping("/find/title")
    public Movie getMovieByTitle(@RequestParam(value="title") String title) {
        Movie movie = movieService.getMovieByTitle(title);
        if(movie == null) {
            throw new RuntimeException("No movie found!");
        }
        return movie;
    }

    @GetMapping(value = "/find/id/{id}")
    public Movie getMovieById(@PathVariable(name = "id") String id) {
        return movieService.getMovieById(id);
    }

    @GetMapping("/popular")
    public List<String> getMostPopularMovies() {
        return movieService.getPopularMovies();
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteMovie(@PathVariable("id") Long id)
    {
        try
        {
            movieService.delete(id);
            rabbitTemplate.convertAndSend("movies-exchange", "movies.deleted",id);
        }
        catch (EmptyResultDataAccessException e)
        {
            Error error = new Error("id", "Movie with requested id cannot be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseWrapper(error));
        }
        catch (Exception e){
            Error error = new Error(null, "Deleting user failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(error));
        }
        return ResponseEntity.ok().build();
    }

}
