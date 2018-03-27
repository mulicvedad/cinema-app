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
    private String firstName;
    @NotNull
    private String lastName;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "person_role", joinColumns = @JoinColumn(name = "person_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<MovieRole> roles = new HashSet<>();

    public MoviePerson() {
    }

    public MoviePerson(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public MoviePerson(@NotNull String firstName, @NotNull String lastName, Set<MovieRole> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
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
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
