package ba.etf.unsa.nwt.userservice.controllers;

import ba.etf.unsa.nwt.userservice.models.Role;
import ba.etf.unsa.nwt.userservice.models.User;
import ba.etf.unsa.nwt.userservice.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
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
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationForm user) throws ServletException
    {
        if(user.firstName.isEmpty() || user.lastName.isEmpty() || user.username.isEmpty() || user.password.isEmpty() || user.email.isEmpty()
                || user.firstName == null || user.lastName == null || user.username == null || user.password == null || user.email == null)
            throw new ServletException("Some fields are not filled in");
        // username and email should be unique
        if(userRepository.isUsernameOrEmailUnique(user.username, user.email) > 0)
            throw new ServletException("Username or email is already in use");
        String passwordHash = BCrypt.hashpw(user.password, BCrypt.gensalt());
        // Registration of new users is only possible for normal users
        Role role = new Role();
        role.setName("ROLE_USER");
        User registeredUser = new User(role, user.firstName, user.lastName, user.username, passwordHash, user.email);
        userRepository.save(registeredUser);
        return ResponseEntity.ok(registeredUser);
    }

    private static class UserRegistrationForm
    {
        public String firstName;
        public String lastName;
        public String username;
        public String password;
        public String email;
    }
}
