package ba.etf.unsa.nwt.userservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "Role ID cannot be null")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private Collection<User> users;

    public Role() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "Role name cannot be null")
    @Size(max = 50, message = "Role name cannot be longer than 50 characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
