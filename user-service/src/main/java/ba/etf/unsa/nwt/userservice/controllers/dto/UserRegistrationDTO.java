package ba.etf.unsa.nwt.userservice.controllers.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRegistrationDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;

    @NotBlank(message = "First name cannot be null or whitespace")
    @Size(max = 50, message = "First name cannot be longer than 50 characters")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotBlank(message = "Last name cannot be null or whitespace")
    @Size(max = 50, message = "Last name cannot be longer than 50 characters")
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
    @Size(max = 100, message = "Password cannot be longer than 100 characters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
