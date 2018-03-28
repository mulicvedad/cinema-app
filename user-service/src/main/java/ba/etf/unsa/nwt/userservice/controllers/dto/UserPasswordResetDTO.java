package ba.etf.unsa.nwt.userservice.controllers.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserPasswordResetDTO {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    @NotBlank(message = "Password cannot be null or whitespace")
    @Size(max = 100, message = "Password cannot be longer than 100 characters")
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @NotBlank(message = "Password cannot be null or whitespace")
    @Size(max = 100, message = "Password cannot be longer than 100 characters")
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @NotBlank(message = "Password cannot be null or whitespace")
    @Size(max = 100, message = "Password cannot be longer than 100 characters")
    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
