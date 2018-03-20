package ba.unsa.etf.nwt.movieservice.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MovieRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;

    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<MoviePersonRole> moviePersonRoles = new HashSet<>();

    public MovieRole() {
    }

    public MovieRole(String role) {
        this.role = role;
    }

    public MovieRole(String role, Set<MoviePersonRole> moviePersonRoles) {
        this.role = role;
        this.moviePersonRoles = moviePersonRoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "MovieRole{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", moviePersonRoles=" + moviePersonRoles +
                '}';
    }
}
