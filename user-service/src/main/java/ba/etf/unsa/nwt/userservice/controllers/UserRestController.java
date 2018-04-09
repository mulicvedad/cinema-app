package ba.etf.unsa.nwt.userservice.controllers;

import ba.etf.unsa.nwt.userservice.controllers.dto.UserPasswordResetDTO;
import ba.etf.unsa.nwt.userservice.controllers.dto.UserRegistrationDTO;
import ba.etf.unsa.nwt.userservice.controllers.dto.UserUpdateDTO;
import ba.etf.unsa.nwt.userservice.models.Error;
import ba.etf.unsa.nwt.userservice.models.ErrorResponseWrapper;
import ba.etf.unsa.nwt.userservice.models.User;
import ba.etf.unsa.nwt.userservice.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("{id}/details")
    public ResponseEntity getUser(@PathVariable("id") Long userId) {
        Optional<User> user = userService.get(userId);
        if (!user.isPresent()) {
            Error error = new Error("id", "User with requested id doesn't exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseWrapper(error));
        }
        return ResponseEntity.ok(user.get().getUserDetails());
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody UserRegistrationDTO user, BindingResult bindingResult) {
        if (userService.getUsernameAndEmailCount(user.getUsername(), user.getEmail()) > 0) {
            Error error = new Error("", "Username or email is already in use");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(error));
        }
        if (bindingResult.hasErrors()) {
            Error error = new Error();
            error.setField(bindingResult.getFieldError().getField());
            error.setMessage(bindingResult.getFieldError().getDefaultMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(error));
        }
        User registeredUser = userService.register(user);
        // for now it's not needed to inform other services
        // rabbitTemplate.convertAndSend("users-exchange","users.created.#",registeredUser.getId());
        return ResponseEntity.ok(registeredUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteAccount(@PathVariable("id") Long id) {
        try {
            userService.delete(id);
            rabbitTemplate.convertAndSend("users-exchange", "users.deleted", id);
        } catch (EmptyResultDataAccessException e) {
            Error error = new Error("id", "User with requested id cannot be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseWrapper(error));
        } catch (Exception e) {
            Error error = new Error("", "Deleting user failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(error));
        }
        return ResponseEntity.ok().build();
    }


    @PutMapping("{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateDTO user) throws ServletException {
        User updatedUser = userService.getLazyInit(id);
        if (userService.getEmailCount(user.getEmail()) > 0) {
            Error error = new Error("email", "Requested email is already in use");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(error));
        }
        updatedUser.setEmail(user.getEmail());
        User us = userService.save(updatedUser);
        // for now it's not needed to inform other services about user changes
        // rabbitTemplate.convertAndSend("users-exchange", "users.updated.#", us.getId()+";"+us.getEmail()+";update");
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/resetpassword")
    public ResponseEntity resetPassword(@PathVariable("id") Long id, @Valid @RequestBody UserPasswordResetDTO user) throws ServletException {
        try {
            //Difference between getOne method - loads whole entity immediately
            Optional<User> userPasswordReset = userService.get(id);
            if (!BCrypt.checkpw(user.getOldPassword(), userPasswordReset.get().getPasswordHash())) {
                Error error = new Error("oldPassword", "Old password is not correct");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(error));
            }
            if (!user.getNewPassword().equals(user.getConfirmNewPassword())) {
                Error error = new Error("confirmNewPassword", "Confirmation of password is not correct");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(error));
            }
            userService.resetPassword(user, userPasswordReset);
        } catch (EntityNotFoundException e) {
            Error error = new Error("id", "User with requested id cannot be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseWrapper(error));
        } catch (Exception e) {
            Error error = new Error("", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(error));
        }
        return ResponseEntity.ok().build();
    }


}
