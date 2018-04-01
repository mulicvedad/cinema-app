package ba.unsa.etf.nwt.movieservice.service;

import ba.unsa.etf.nwt.movieservice.model.*;
import ba.unsa.etf.nwt.movieservice.repository.GenreRepository;
import ba.unsa.etf.nwt.movieservice.repository.MoviePersonRepository;
import ba.unsa.etf.nwt.movieservice.repository.MovieRepository;
import ba.unsa.etf.nwt.movieservice.repository.MovieRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MovieService {
    @Autowired private MovieRepository movieRepository;
    @Autowired private GenreRepository genreRepository;
    @Autowired private MoviePersonRepository moviePersonRepository;
    @Autowired private MovieRoleRepository movieRoleRepository;

    public void addNewMovie(MovieCreationRequest movieRequest) {
        Movie movie = movieRequest.getMovie();
        Set<Genre> movieGenres = getGenres(movieRequest.getGenres());
        Set<MoviePerson> moviePeople = getPeople(movieRequest.getMoviePeople());
        movie.setGenres(movieGenres);
        movie.setMoviePeople(moviePeople);
        movieRepository.save(movie);
    }

    public Movie getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    private Set<Genre> getGenres(Set<Genre> genres) {
        Set<Genre> movieGenres = new HashSet<>();

        for (Genre g: genres) {
            Genre foundGenre = genreRepository.findByName(g.getName());
            if(foundGenre != null) {
                movieGenres.add(foundGenre);
            } else {
                movieGenres.add(g);
            }
        }
        return movieGenres;
    }

    private Set<MoviePerson> getPeople(HashSet<MoviePerson> moviePeople) {
        Set<MoviePerson> moviePersonSet = new HashSet<>();

        for (MoviePerson mp: moviePeople) {
            MoviePerson moviePerson = moviePersonRepository.findByFirstNameAndLastName(mp.getFirstName(), mp.getLastName());
            if(moviePerson != null) {
                moviePersonSet.add(moviePerson);
            } else {
               Set<MovieRole> movieRoles = getMovieRoles(mp.getRoles());
               mp.setRoles(movieRoles);
               moviePersonRepository.save(mp);
               moviePersonSet.add(mp);
            }
        }

        return moviePersonSet;
    }

    private Set<MovieRole> getMovieRoles(Set<MovieRole> movieRoles) {
        Set<MovieRole> roles = new HashSet<>();
        for (MovieRole role: movieRoles) {
            MovieRole movieRole = movieRoleRepository.findByRole(role.getRole());
            if(movieRole != null) {
                roles.add(movieRole);
            } else {
                roles.add(role);
            }
        }
        return roles;
    }
}
