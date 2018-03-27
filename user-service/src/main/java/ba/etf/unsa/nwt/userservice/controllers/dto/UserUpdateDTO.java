package ba.etf.unsa.nwt.userservice.controllers.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserUpdateDTO {
    private String email;
    // should add gender field
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
