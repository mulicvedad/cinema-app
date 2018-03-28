package ba.etf.unsa.nwt.userservice.models;

import javax.persistence.*;
<<<<<<< HEAD
=======
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;
>>>>>>> user-service-setup

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
<<<<<<< HEAD
=======
    @NotNull(message = "User ID cannot be null")
>>>>>>> user-service-setup
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    private String firstName;
    private String lastName;
    private String username;
    private String passwordHash;
    private String email;

<<<<<<< HEAD
    public User(Role role, String firstName, String lastName, String passwordHash, String email)
=======
    public User(Role role, String firstName, String lastName, String username, String passwordHash, String email)
>>>>>>> user-service-setup
    {
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
<<<<<<< HEAD
=======
        this.username = username;
>>>>>>> user-service-setup
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

<<<<<<< HEAD
    @Column(name = "first_name", nullable = false, length = 50)
=======
    @NotBlank(message = "First name cannot be null or whitespace")
    @Size(max = 50, message = "First name cannot be longer than 50 characters")
    @Column(name = "first_name")
>>>>>>> user-service-setup
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
<<<<<<< HEAD
    @Column(name = "last_name", nullable = false, length = 50)
=======

    @NotBlank(message = "Last name cannot be null or whitespace")
    @Size(max = 50, message = "Last name cannot be longer than 50 characters")
    @Column(name = "last_name")
>>>>>>> user-service-setup
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

<<<<<<< HEAD
    @Column(nullable = false, length = 50)
=======
    @NotBlank(message = "Username cannot be null or whitespace")
    @Size(max = 50, message = "Username cannot be longer than 50 characters")
    @Column(unique = true)
>>>>>>> user-service-setup
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

<<<<<<< HEAD
    @Column(name="password_hash", nullable = false, length = 2000)
=======
    @NotBlank(message = "Password cannot be null or whitespace")
    @Size(max = 2000, message = "Hashed password cannot be longer than 2000 characters")
    @Column(name="password_hash")
>>>>>>> user-service-setup
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

<<<<<<< HEAD
    @Column(nullable = false, length = 100)
=======
    @NotBlank(message = "Email cannot be null or whitespace")
    @Size(max = 100, message = "Email cannot be longer than 100 characters")
    @Email(message = "Email should be valid")
    @Column(unique = true)
>>>>>>> user-service-setup
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
<<<<<<< HEAD
=======

    public Map getUserDetails()
    {
        Map<Object, Object> map = new HashMap<>();
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("username", username);
        map.put("email", email);
        return map;
    }
>>>>>>> user-service-setup
}
