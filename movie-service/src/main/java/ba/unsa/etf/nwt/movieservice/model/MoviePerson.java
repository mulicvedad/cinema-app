package ba.unsa.etf.nwt.movieservice.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MoviePerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<MoviePersonRole> moviePersonRoles = new HashSet<>();

    public MoviePerson() {
    }

    public MoviePerson(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public MoviePerson(String firstName, String lastName, Set<MoviePersonRole> moviePersonRoles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.moviePersonRoles = moviePersonRoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<MoviePersonRole> getMoviePersonRoles() {
        return moviePersonRoles;
    }

    public void setMoviePersonRoles(Set<MoviePersonRole> moviePersonRoles) {
        this.moviePersonRoles = moviePersonRoles;
    }

    @Override
    public String toString() {
        return "MoviePerson{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", moviePersonRoles=" + moviePersonRoles +
                '}';
    }
}
