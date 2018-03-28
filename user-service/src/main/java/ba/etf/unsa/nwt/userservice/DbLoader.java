package ba.etf.unsa.nwt.userservice;

import ba.etf.unsa.nwt.userservice.models.Role;
import ba.etf.unsa.nwt.userservice.models.User;
import ba.etf.unsa.nwt.userservice.repositories.RoleRepository;
import ba.etf.unsa.nwt.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbLoader implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception
    {
        if(roleRepository.count() == 0) {
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            roleRepository.save(role);
            role = new Role();
            role.setName("ROLE_USER");
            roleRepository.save(role);
        }
        if(userRepository.count() == 0) {
            Role role = roleRepository.findByName("ROLE_ADMIN");
            User user = new User(role, "Admin", "Adminkovic", "admin","21232f297a57a5a743894a0e4a801fc3", "admin@etf.unsa.ba");
            userRepository.save(user);

            role = roleRepository.findByName("ROLE_USER");
            user = new User(role, "Neko", "Nekic", "nnekic1", "5f4dcc3b5aa765d61d8327deb882cf99", "nnekic1@etf.unsa.ba");
            userRepository.save(user);
        }
    }
}
