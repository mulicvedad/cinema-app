package ba.unsa.etf.nwt.movieservice.service;

import ba.unsa.etf.nwt.movieservice.model.Movie;
import ba.unsa.etf.nwt.movieservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public void addNewMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public Movie getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }
}
