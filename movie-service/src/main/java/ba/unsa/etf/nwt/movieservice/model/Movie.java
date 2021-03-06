package ba.unsa.etf.nwt.movieservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    @JsonProperty("release_date")
    private Date releaseDate;

    @NotNull
    @Size(max = 255555555)
    private String overview;

    @NotNull
    @JsonProperty("poster_path")
    private String posterPath;

    @NotNull
    private String largePosterPath;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("vote_average")
    private Float averageVote;

    private Long tmdbId;

    @ManyToMany
    @JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movie_review")
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movie_person_role", joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "person_role_id"))
    private Set<MoviePerson> moviePeople = new HashSet<>();

    public Movie() {
    }

    public Movie(@NotNull String title, Date releaseDate, String overview, String posterPath, String largePosterPath, String originalTitle, Float averageVote) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.posterPath = posterPath;
        this.largePosterPath = largePosterPath;
        this.originalTitle = originalTitle;
        this.averageVote = averageVote;
    }

    public Movie(@NotNull String title, Date releaseDate, String overview, String posterPath, String largePosterPath, Set<Genre> genres) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.posterPath = posterPath;
        this.largePosterPath = largePosterPath;
        this.genres = genres;
}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getLargePosterPath() {
        return largePosterPath;
    }

    public void setLargePosterPath(String largePosterPath) {
        this.largePosterPath = largePosterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Float getAverageVote() {
        return averageVote;
    }

    public void setAverageVote(Float averageVote) {
        this.averageVote = averageVote;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<MoviePerson> getMoviePeople() {
        return moviePeople;
    }

    public void setMoviePeople(Set<MoviePerson> moviePeople) {
        this.moviePeople = moviePeople;
    }

    public Long getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(releaseDate, movie.releaseDate) &&
                Objects.equals(overview, movie.overview) &&
                Objects.equals(posterPath, movie.posterPath) &&
                Objects.equals(largePosterPath, movie.largePosterPath) &&
                Objects.equals(originalTitle, movie.originalTitle) &&
                Objects.equals(averageVote, movie.averageVote) &&
                Objects.equals(tmdbId, movie.tmdbId) &&
                Objects.equals(genres, movie.genres) &&
                Objects.equals(reviews, movie.reviews) &&
                Objects.equals(moviePeople, movie.moviePeople);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, releaseDate, overview, posterPath, largePosterPath, originalTitle, averageVote, tmdbId, genres, reviews, moviePeople);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", overview='" + overview + '\'' +
                ", posterPath='" + posterPath + '\'' +
                '}';
    }
}
