package ba.unsa.etf.nwt.authservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "Role ID cannot be null")
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private Collection<UserAccount> userAccounts;

    @OneToMany(mappedBy = "role")
    private List<AccessPermission> permissions;

    public Role() { }

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

    public Collection<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Collection<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }

    @Override
    public String toString() {
        return name;
    }
}