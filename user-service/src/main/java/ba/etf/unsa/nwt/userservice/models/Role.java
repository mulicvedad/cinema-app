package ba.etf.unsa.nwt.userservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
<<<<<<< HEAD
=======
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
>>>>>>> user-service-setup
import java.util.Collection;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
<<<<<<< HEAD
=======
    @NotNull(message = "Role ID cannot be null")
>>>>>>> user-service-setup
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

<<<<<<< HEAD
    @Column(nullable = false, length=50)
=======
    @NotBlank(message = "Role name cannot be null or whitespace")
    @Size(max = 50, message = "Role name cannot be longer than 50 characters")
>>>>>>> user-service-setup
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
