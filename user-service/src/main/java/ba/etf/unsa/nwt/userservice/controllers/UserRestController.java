package ba.etf.unsa.nwt.userservice.controllers;

import ba.etf.unsa.nwt.userservice.controllers.dto.ErrorDetailsDTO;
import ba.etf.unsa.nwt.userservice.controllers.dto.UserPasswordResetDTO;
import ba.etf.unsa.nwt.userservice.controllers.dto.UserRegistrationDTO;
import ba.etf.unsa.nwt.userservice.controllers.dto.UserUpdateDTO;
import ba.etf.unsa.nwt.userservice.models.Role;
import ba.etf.unsa.nwt.userservice.models.User;
import ba.etf.unsa.nwt.userservice.repositories.RoleRepository;
import ba.etf.unsa.nwt.userservice.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public Collection<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @GetMapping("{id}/details")
    public ResponseEntity getUser(@PathVariable("id") Long userId) throws ServletException {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) {
            ErrorDetailsDTO error = new ErrorDetailsDTO("id","User with requested id doesn't exist", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        return ResponseEntity.ok(user.get().getUserDetails());
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody UserRegistrationDTO user, BindingResult bindingResult) throws ServletException
    {
       if(userRepository.isUsernameOrEmailUnique(user.getUsername(), user.getEmail()) > 0) {
           ErrorDetailsDTO error = new ErrorDetailsDTO("","Username or email is already in use", HttpStatus.BAD_REQUEST.value());
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
       }
        if (bindingResult.hasErrors()) {
            ErrorDetailsDTO error = new ErrorDetailsDTO();
            error.setField(bindingResult.getFieldError().getField());
            error.setMessage(bindingResult.getFieldError().getDefaultMessage());
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        String passwordHash = hashPassword(user.getPassword());
        // Registration of new users is only possible for normal users
        Role role = roleRepository.findByName("ROLE_USER");
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
            ErrorDetailsDTO error = new ErrorDetailsDTO("id", "User with requested id cannot be found", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        catch (Exception e)
        {
            ErrorDetailsDTO error = new ErrorDetailsDTO("", "Deleting user failed", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        return ResponseEntity.ok().build();
    }


    @PutMapping("{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long id,@Valid @RequestBody UserUpdateDTO user) throws ServletException
    {
        //Lazy initialization
        User updatedUser = userRepository.getOne(id);
        if (userRepository.isEmailUnique(user.getEmail()) > 0) {
            ErrorDetailsDTO error = new ErrorDetailsDTO("email", "Requested email is already in use", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        updatedUser.setEmail(user.getEmail());
        userRepository.save(updatedUser);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/resetpassword")
    public ResponseEntity resetPassword(@PathVariable("id") Long id, @Valid @RequestBody UserPasswordResetDTO user) throws ServletException
    {
        try {
            //Difference between getOne method - loads whole entity immediately
            Optional<User> userPasswordReset = userRepository.findById(id);
            if (!BCrypt.checkpw(user.getOldPassword(),userPasswordReset.get().getPasswordHash())) {
                ErrorDetailsDTO error = new ErrorDetailsDTO("oldPassword", "Old password is not correct", HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            if (!user.getNewPassword().equals(user.getConfirmNewPassword())) {
                ErrorDetailsDTO error = new ErrorDetailsDTO("confirmNewPassword", "Confirmation of password is not correct", HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            userPasswordReset.get().setPasswordHash(hashPassword(user.getNewPassword()));
            userRepository.save(userPasswordReset.get());
        }
        catch (EntityNotFoundException e)
        {
            ErrorDetailsDTO error = new ErrorDetailsDTO("id", "User with requested id cannot be found", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        catch (Exception e)
        {
            ErrorDetailsDTO error = new ErrorDetailsDTO("", e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        return ResponseEntity.ok().build();
    }


    private static String hashPassword(String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}