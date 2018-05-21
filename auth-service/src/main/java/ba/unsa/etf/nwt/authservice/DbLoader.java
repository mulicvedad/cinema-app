package ba.unsa.etf.nwt.authservice;

import ba.unsa.etf.nwt.authservice.models.Role;
import ba.unsa.etf.nwt.authservice.models.UserAccount;
import ba.unsa.etf.nwt.authservice.services.RoleService;
import ba.unsa.etf.nwt.authservice.services.UserAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DbLoader implements CommandLineRunner {

    @Autowired
    private UserAccountService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        if (roleService.count() == 0) {
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            roleService.save(role);
            role = new Role();
            role.setName("ROLE_USER");
            roleService.save(role);
        }
        if (userService.count() == 0) {
            // Password for all users is pw = password
            Optional<Role> role = roleService.find("ROLE_ADMIN");
            UserAccount user = new UserAccount("admin", "$2a$10$iIFEUH6ORbNM7HSy7cpDcuPqHPQL4l9hxR/TLz9lz.RK/fMlkxA5G",
                    "admin@leva.etf.unsa.ba", role.get(), true);
            userService.save(user);

            role = roleService.find("ROLE_USER");
            user = new UserAccount("user", "$2a$10$iIFEUH6ORbNM7HSy7cpDcuPqHPQL4l9hxR/TLz9lz.RK/fMlkxA5G",
                    "user@leva.etf.unsa.ba", role.get(), false);
            userService.save(user);
        }
    }

}
