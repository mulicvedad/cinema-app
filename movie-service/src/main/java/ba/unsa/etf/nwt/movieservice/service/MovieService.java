package ba.unsa.etf.nwt.movieservice.service;

import ba.unsa.etf.nwt.movieservice.model.*;
import ba.unsa.etf.nwt.movieservice.repository.GenreRepository;
import ba.unsa.etf.nwt.movieservice.repository.MoviePersonRepository;
import ba.unsa.etf.nwt.movieservice.repository.MovieRepository;
import ba.unsa.etf.nwt.movieservice.repository.MovieRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private static final String DISCOVER_URL =
            "https://api.themoviedb.org/3/discover/movie";
    private static final String FIND_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = "?api_key=";
    private static final String POPULARITY_FILTER =
            "&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
    private static final String FIND_URL_PARAMS = "&language=en-US";

    @Value("${tmdb.apikey}")
    private String apiKey;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private MoviePersonRepository moviePersonRepository;
    @Autowired
    private MovieRoleRepository movieRoleRepository;
    @Autowired
    private RestTemplate restTemplate;

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

    public List<String> getPopularMovies() {
        String url = DISCOVER_URL + API_KEY + apiKey + POPULARITY_FILTER;
        List<Movie> mostPopularMovies = restTemplate.getForObject(url, TmdbMovieResponse.class).getResults();
        return mostPopularMovies
                .stream()
                .map(Movie::getTitle)
                .collect(Collectors.toList());
    }

    public Movie getMovieById(String id) {
        String url = FIND_URL + id + API_KEY + apiKey + FIND_URL_PARAMS;
        Movie movie = restTemplate.getForObject(url, Movie.class);
        Movie existingMovie = movieRepository.findByTitle(movie.getTitle());
        if(existingMovie == null) {
            return createMovieFromTmdb(movie);
        }
        return existingMovie;
    }

    private Set<Genre> getGenres(Set<Genre> genres) {
        Set<Genre> movieGenres = new HashSet<>();

        for (Genre g : genres) {
            Genre foundGenre = genreRepository.findByName(g.getName());
            if (foundGenre != null) {
                movieGenres.add(foundGenre);
            } else {
                movieGenres.add(g);
            }
        }
        return movieGenres;
    }

    private Set<MoviePerson> getPeople(HashSet<MoviePerson> moviePeople) {
        Set<MoviePerson> moviePersonSet = new HashSet<>();

        for (MoviePerson mp : moviePeople) {
            MoviePerson moviePerson = moviePersonRepository.findByName(mp.getName());
            if (moviePerson != null) {
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
        for (MovieRole role : movieRoles) {
            MovieRole movieRole = movieRoleRepository.findByRole(role.getRole());
            if (movieRole != null) {
                roles.add(movieRole);
            } else {
                roles.add(role);
            }
        }
        return roles;
    }

    private Movie createMovieFromTmdb(Movie movie) {
        movie.setTmdbId(movie.getId());
        movie.setId(null);
        movie.getGenres().forEach(g -> g.setId(null));
        movie.setMoviePeople(getMovieCast(movie.getTmdbId()));
        addNewMovie(new MovieCreationRequest(movie, (HashSet<Genre>) movie.getGenres(), (HashSet<MoviePerson>) movie.getMoviePeople()));
        return movieRepository.findByTitle(movie.getTitle());
    }

    private Set<MoviePerson> getMovieCast(Long id) {
        String url = FIND_URL + id + "/credits" + API_KEY + apiKey;
        TmdbCreditsResponse creditsResponse = restTemplate.getForObject(url, TmdbCreditsResponse.class);
        Set<MoviePerson> moviePeople = new HashSet<>();

        for (Crew c: creditsResponse.getCrew()) {
            Set<MovieRole> roles = new HashSet<>();
            MoviePerson person = new MoviePerson(c.getName());
            roles.add(new MovieRole(c.getJob()));
            person.setRoles(getMovieRoles(roles));
            moviePeople.add(person);
        }
        return moviePeople;
    }
}
