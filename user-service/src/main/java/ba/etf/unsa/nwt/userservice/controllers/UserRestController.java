package ba.etf.unsa.nwt.userservice.controllers;

import ba.etf.unsa.nwt.userservice.controllers.dto.ErrorDetailsDTO;
import ba.etf.unsa.nwt.userservice.controllers.dto.UserPasswordResetDTO;
import ba.etf.unsa.nwt.userservice.controllers.dto.UserRegistrationDTO;
import ba.etf.unsa.nwt.userservice.controllers.dto.UserUpdateDTO;
import ba.etf.unsa.nwt.userservice.models.Role;
import ba.etf.unsa.nwt.userservice.models.User;
import ba.etf.unsa.nwt.userservice.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Collection<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @GetMapping("{id}/details")
    public Map getUser(@PathVariable("id") Long userId) throws ServletException {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent())
            throw new ServletException("User with requested id doesn't exist");
        return user.get().getUserDetails();
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody UserRegistrationDTO user, BindingResult bindingResult) throws ServletException
    {
       if(userRepository.isUsernameOrEmailUnique(user.getUsername(), user.getEmail()) > 0)
            throw new ServletException("Username or email is already in use");
        if (bindingResult.hasErrors()) {
            ErrorDetailsDTO error = new ErrorDetailsDTO();
            error.setField(bindingResult.getFieldError().getField());
            error.setMessage(bindingResult.getFieldError().getDefaultMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        String passwordHash = hashPassword(user.getPassword());
        // Registration of new users is only possible for normal users
        Role role = new Role();
        role.setName("ROLE_USER");
        User registeredUser = new User(role, user.getFirstName(), user.getLastName(), user.getUsername(), passwordHash, user.getEmail());
        userRepository.save(registeredUser);
        return ResponseEntity.ok(registeredUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) throws ServletException
    {
        try {
            userRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with requested id cannot be found");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deleting user failed");
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long id,@Valid @RequestBody UserUpdateDTO user) throws ServletException
    {

        User updatedUser = userRepository.getOne(id);
        if (userRepository.isEmailUnique(user.getEmail()) > 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested email is already in use");
        userRepository.save(updatedUser);
        return ResponseEntity.ok(updatedUser);
    }
    @PutMapping("{id}/resetpassword")
    public ResponseEntity resetPassword(@PathVariable("id") Long id, @Valid @RequestBody UserPasswordResetDTO user) throws ServletException
    {
        User userPasswordReset = userRepository.getOne(id);
        if(userPasswordReset.getPasswordHash() != hashPassword(user.getOldPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is not correct");
        if(user.getNewPassword() != user.getConfirmNewPassword())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Confirmation of password is not correct");
        userPasswordReset.setPasswordHash(hashPassword(user.getNewPassword()));
        userRepository.save(userPasswordReset);
        return ResponseEntity.status(HttpStatus.OK).body("Password successfully changed");
    }

    private static String hashPassword(String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
