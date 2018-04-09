package ba.etf.unsa.nwt.userservice.services;

import ba.etf.unsa.nwt.userservice.controllers.dto.UserPasswordResetDTO;
import ba.etf.unsa.nwt.userservice.controllers.dto.UserRegistrationDTO;
import ba.etf.unsa.nwt.userservice.models.Role;
import ba.etf.unsa.nwt.userservice.models.User;
import ba.etf.unsa.nwt.userservice.repositories.RoleRepository;
import ba.etf.unsa.nwt.userservice.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public Collection<User> getAll()
    {
        return userRepository.findAll();
    }

    public Optional<User> get(Long id)
    {
        return userRepository.findById(id);
    }

    public User getLazyInit(Long id)
    {
        //Lazy initialization
        return userRepository.getOne(id);
    }

    public User save(User user)
    {
        return userRepository.save(user);
    }
    public void delete(Long id)
    {
        userRepository.deleteById(id);
    }

    public int getUsernameAndEmailCount(String username, String email)
    {
        return userRepository.isUsernameOrEmailUnique(username, email);
    }

    public int getEmailCount(String email)
    {
        return userRepository.isEmailUnique(email);
    }

    public User register(@Valid @RequestBody UserRegistrationDTO user) {
        String passwordHash = hashPassword(user.getPassword());
        // Registration of new users is only possible for normal users
        Role role = roleRepository.findByName("ROLE_USER");
        User registeredUser = new User(role, user.getFirstName(), user.getLastName(), user.getUsername(), passwordHash, user.getEmail());
        userRepository.save(registeredUser);
        return registeredUser;
    }

    public void resetPassword(@Valid @RequestBody UserPasswordResetDTO user, Optional<User> userPasswordReset) {
        userPasswordReset.get().setPasswordHash(hashPassword(user.getNewPassword()));
        userRepository.save(userPasswordReset.get());
    }


    private static String hashPassword(String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
