package ba.unsa.etf.nwt.movieservice.model;

import javax.persistence.*;

@Entity
public class MovieRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;

    public MovieRole() {
    }

    public MovieRole(String role) {
        this.role = role;
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
                '}';
    }
}
