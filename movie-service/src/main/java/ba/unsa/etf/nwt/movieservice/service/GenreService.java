package ba.unsa.etf.nwt.movieservice.service;

import ba.unsa.etf.nwt.movieservice.model.Genre;
import ba.unsa.etf.nwt.movieservice.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }
}
