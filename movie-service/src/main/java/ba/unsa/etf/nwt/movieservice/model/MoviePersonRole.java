package ba.unsa.etf.nwt.movieservice.model;

import javax.persistence.*;

@Entity
public class MoviePersonRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "person_id")
    private MoviePerson person;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "role_id")
    private MovieRole role;

    public MoviePersonRole(MoviePerson person, Movie movie, MovieRole role) {
        this.person = person;
        this.movie = movie;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MoviePerson getPerson() {
        return person;
    }

    public void setPerson(MoviePerson person) {
        this.person = person;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MovieRole getRole() {
        return role;
    }

    public void setRole(MovieRole role) {
        this.role = role;
    }
}
