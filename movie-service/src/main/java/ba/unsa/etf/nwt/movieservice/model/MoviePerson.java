package ba.unsa.etf.nwt.movieservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MoviePerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "person_role", joinColumns = @JoinColumn(name = "person_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<MovieRole> roles = new HashSet<>();

    public MoviePerson() {
    }

    public MoviePerson(String name) {
        this.name = name;
    }

    public MoviePerson(@NotNull String name, Set<MovieRole> roles) {
        this.name = name;
        this.roles = roles;
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

    public Set<MovieRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<MovieRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "MoviePerson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roles=" + roles +
                '}';
    }
}
