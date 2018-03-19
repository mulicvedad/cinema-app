package ba.etf.unsa.nwt.userservice.Controllers;

import ba.etf.unsa.nwt.userservice.Models.Role;
import ba.etf.unsa.nwt.userservice.Models.User;
import ba.etf.unsa.nwt.userservice.Repositories.RoleRepository;
import ba.etf.unsa.nwt.userservice.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;


@Controller
@RequestMapping(path = "/demo")
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(path = "/add_roles")
    // Hard-coded for demonstration purposes
    public @ResponseBody String addNewRole ()
    {
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);

        String firstMessage = "Role user saved\n";
        role = new Role();
        role.setName("ROLE_ADMIN");
        roleRepository.save(role);
        String secondMessage = "\nRole admin saved";
        return firstMessage + secondMessage;
    }

    @GetMapping(path = "/all_roles")
    public @ResponseBody Iterable<Role> getAllRoles()
    {
        return roleRepository.findAll();
    }

    @GetMapping(path = "/all_users")
    public @ResponseBody Iterable<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @GetMapping(path = "/add_users")
    public @ResponseBody String addNewUser()
    {
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("Adminkovic");
        user.setPasswordHash("21232f297a57a5a743894a0e4a801fc3");
        user.setEmail("admin@etf.unsa.ba");
        user.setUsername("admin");
        Role role = roleRepository.findByName("ROLE_ADMIN");
        user.setRole(role);
        userRepository.save(user);
        String firstMessage = "Saved admin user\n";

        user = new User();
        user.setFirstName("Neko");
        user.setLastName("Nekic");
        user.setPasswordHash("5f4dcc3b5aa765d61d8327deb882cf99");
        user.setEmail("nnekic1@etf.unsa.ba");
        user.setUsername("nnekic1");
        role = roleRepository.findByName("ROLE_USER");
        user.setRole(role);
        userRepository.save(user);
        String secondMessage = "\nSaved default user";

        return firstMessage + secondMessage;
    }

}
