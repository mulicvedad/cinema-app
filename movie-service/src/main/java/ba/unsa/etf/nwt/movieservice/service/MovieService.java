package ba.unsa.etf.nwt.movieservice.service;

import ba.unsa.etf.nwt.movieservice.model.*;
import ba.unsa.etf.nwt.movieservice.repository.GenreRepository;
import ba.unsa.etf.nwt.movieservice.repository.MoviePersonRepository;
import ba.unsa.etf.nwt.movieservice.repository.MovieRepository;
import ba.unsa.etf.nwt.movieservice.repository.MovieRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Service
public class MovieService {
    private static final String DISCOVER_URL =
            "https://api.themoviedb.org/3/discover/movie";
    private static final String FIND_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = "?api_key=5b5a9f89ddd9f24679aa33e73cdd0a7a";
    private static final String POPULARITY_FILTER =
            "&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
    private static final String FIND_URL_PARAMS = "&language=en-US";

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

    public TmdbResponse getPopularMovies() {
        String url = DISCOVER_URL + API_KEY + POPULARITY_FILTER;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, TmdbResponse.class);
    }

    public Movie getMovieById(String id) {
        String url = FIND_URL +  id + API_KEY + FIND_URL_PARAMS;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, Movie.class);
    }
}
