package ba.unsa.etf.nwt.movieservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
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
    private Timestamp releaseDate;
    @NotNull
    private String plot;
    @NotNull
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

    public Movie(@NotNull String title, Timestamp releaseDate, String plot, String posterPath) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.plot = plot;
        this.posterPath = posterPath;
    }

    public Movie(@NotNull String title, Timestamp releaseDate, String plot, String posterPath, Set<Genre> genres) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.plot = plot;
        this.posterPath = posterPath;
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

    public Timestamp getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Timestamp releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
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
                ", plot='" + plot + '\'' +
                ", posterPath='" + posterPath + '\'' +
                '}';
    }
}
