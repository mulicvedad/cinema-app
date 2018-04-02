package ba.unsa.etf.nwt.movieservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Movie {
    @JsonProperty("id")
    private int tmdbId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long movieId;
    @NotNull
    @Size(max = 255)
    private String title;
    @NotNull
    @JsonProperty("release_date")
    private Timestamp releaseDate;
    @NotNull
    private String overview;
    @NotNull
    @JsonProperty("poster_path")
    private String posterPath;


    @ManyToMany(cascade = CascadeType.ALL)
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

    public Movie(@NotNull String title, Timestamp releaseDate, String overview, String posterPath) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public Movie(@NotNull String title, Timestamp releaseDate, String overview, String posterPath, Set<Genre> genres) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.posterPath = posterPath;
        this.genres = genres;
    }

    public Long getId() {
        return movieId;
    }

    public void setId(Long id) {
        this.movieId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Timestamp releaseDate) {
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
