package ba.unsa.etf.nwt.movieservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String title;
    private Timestamp releaseDate;
    private String plot;
    private String posterPath;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private Set<Genre> genres;

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

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", plot='" + plot + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", genres=" + genres +
                '}';
    }
}
