package ba.etf.unsa.nwt.userservice.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "User ID cannot be null")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    private String firstName;
    private String lastName;
    private String username;
    private String passwordHash;
    private String email;

    public User(Role role, String firstName, String lastName, String username, String passwordHash, String email)
    {
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
    }
    public  User() {}

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

    @NotBlank(message = "First name cannot be null or whitespace")
    @Size(max = 50, message = "First name cannot be longer than 50 characters")
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotBlank(message = "Last name cannot be null or whitespace")
    @Size(max = 50, message = "Last name cannot be longer than 50 characters")
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotBlank(message = "Username cannot be null or whitespace")
    @Size(max = 50, message = "Username cannot be longer than 50 characters")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "Password cannot be null or whitespace")
    @Size(max = 2000, message = "Hashed password cannot be longer than 2000 characters")
    @Column(name="password_hash")
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @NotBlank(message = "Email cannot be null or whitespace")
    @Size(max = 100, message = "Email cannot be longer than 100 characters")
    @Email(message = "Email should be valid")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map getUserDetails()
    {
        Map<Object, Object> map = new HashMap<>();
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("username", username);
        map.put("email", email);
        return map;
    }
}
