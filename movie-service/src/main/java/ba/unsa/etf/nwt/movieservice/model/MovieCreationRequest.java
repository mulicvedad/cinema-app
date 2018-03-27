package ba.unsa.etf.nwt.movieservice.model;

import java.util.HashSet;

public class MovieCreationRequest {
    private Movie movie;
    private HashSet<Genre> genres;
    private HashSet<MoviePerson> moviePeople;

    public MovieCreationRequest() {
    }

    public MovieCreationRequest(Movie movie, HashSet<Genre> genres, HashSet<MoviePerson> moviePeople) {
        this.movie = movie;
        this.genres = genres;
        this.moviePeople = moviePeople;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public HashSet<Genre> getGenres() {
        return genres;
    }

    public void setGenres(HashSet<Genre> genres) {
        this.genres = genres;
    }

    public HashSet<MoviePerson> getMoviePeople() {
        return moviePeople;
    }

    public void setMoviePeople(HashSet<MoviePerson> moviePeople) {
        this.moviePeople = moviePeople;
    }
}
