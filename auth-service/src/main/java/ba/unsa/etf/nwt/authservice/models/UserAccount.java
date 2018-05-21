package ba.unsa.etf.nwt.authservice.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "UserAccount ID cannot be null")
    private Long id;

    //@NotNull(message = "UserAccount ID cannot be null")
    //@Column(unique = true)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;
    private String username;
    private String passwordHash;
    private String email;
    private boolean isAdmin;

    public UserAccount(String username, String passwordHash, String email, Role role, boolean isAdmin) {
        this.role = role;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public UserAccount() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Map<String, Object> getUserDto() {
        Map<String, Object> userDto = new HashMap<>();
        userDto.put("id", this.id);
        userDto.put("username", this.username);
        userDto.put("email", this.email);
        userDto.put("roles", this.role.toString());
        return userDto;
    }
}