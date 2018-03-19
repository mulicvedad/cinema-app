package ba.unsa.etf.nwt.movieservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String name;
    @ManyToMany(mappedBy = "genres")
    private Set<Movie> movies;

    public Genre() {
    }

    public Genre(@NotNull String name) {
        this.name = name;
    }

    public Genre(@NotNull String name, Set<Movie> movies) {
        this.name = name;
        this.movies = movies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
