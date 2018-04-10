package ba.etf.unsa.nwt.userservice;

import ba.etf.unsa.nwt.userservice.models.Role;
import ba.etf.unsa.nwt.userservice.models.User;
import ba.etf.unsa.nwt.userservice.services.RoleService;
import ba.etf.unsa.nwt.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbLoader implements CommandLineRunner {
    @Autowired
    private UserService userService;
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

            // Password of all users is pw = password
            Role role = roleService.find("ROLE_ADMIN");
            User user = new User(role, "Admin", "Admin", "admin", "$2a$10$iIFEUH6ORbNM7HSy7cpDcuPqHPQL4l9hxR/TLz9lz.RK/fMlkxA5G", "admin@etf.unsa.ba");
            userService.save(user);

            role = roleService.find("ROLE_USER");
            user = new User(role, "Jeffery", "Carter", "jcarter", "$2a$10$iIFEUH6ORbNM7HSy7cpDcuPqHPQL4l9hxR/TLz9lz.RK/fMlkxA5G", "JefferyCarter@rhyta.com");
            userService.save(user);

            role = roleService.find("ROLE_USER");
            user = new User(role, "Susan", "Nagy", "Dichanny", "$2a$10$iIFEUH6ORbNM7HSy7cpDcuPqHPQL4l9hxR/TLz9lz.RK/fMlkxA5G", "SusanKNagy@jourrapide.com");
            userService.save(user);

            role = roleService.find("ROLE_USER");
            user = new User(role, "Latoya", "Forest", "Draggell", "$2a$10$iIFEUH6ORbNM7HSy7cpDcuPqHPQL4l9hxR/TLz9lz.RK/fMlkxA5G", "LatoyaBForrest@teleworm.us");
            userService.save(user);

            role = roleService.find("ROLE_USER");
            user = new User(role, "Michael", "Remley", "Antrader", "$2a$10$iIFEUH6ORbNM7HSy7cpDcuPqHPQL4l9hxR/TLz9lz.RK/fMlkxA5G", "MichaelDRemley@armyspy.com");
            userService.save(user);

            role = roleService.find("ROLE_USER");
            user = new User(role, "Joseph", "Newborn", "stainges", "$2a$10$iIFEUH6ORbNM7HSy7cpDcuPqHPQL4l9hxR/TLz9lz.RK/fMlkxA5G", "JosephSNewborn@teleworm.us");
            userService.save(user);

            role = roleService.find("ROLE_USER");
            user = new User(role, "Audrey", "Goggin", "amettelly", "$2a$10$iIFEUH6ORbNM7HSy7cpDcuPqHPQL4l9hxR/TLz9lz.RK/fMlkxA5G", "AudreyJGoggin@rhyta.com");
            userService.save(user);

            role = roleService.find("ROLE_USER");
            user = new User(role, "Steven", "Straub", "liented23", "$2a$10$iIFEUH6ORbNM7HSy7cpDcuPqHPQL4l9hxR/TLz9lz.RK/fMlkxA5G", "StevenMStraub@rhyta.com");
            userService.save(user);

            role = roleService.find("ROLE_USER");
            user = new User(role, "William", "Wertz", "barturponat55", "$2a$10$iIFEUH6ORbNM7HSy7cpDcuPqHPQL4l9hxR/TLz9lz.RK/fMlkxA5G", "william.wertz@jourrapide.com");
            userService.save(user);

            role = roleService.find("ROLE_USER");
            user = new User(role, "Lester", "Coyle", "frinum", "$2a$10$iIFEUH6ORbNM7HSy7cpDcuPqHPQL4l9hxR/TLz9lz.RK/fMlkxA5G", "lesterLcoyle.wertz@teleworm.us");
            userService.save(user);

            role = roleService.find("ROLE_USER");
            user = new User(role, "Cheryl", "Johnson", "ander1935", "$2a$10$iIFEUH6ORbNM7HSy7cpDcuPqHPQL4l9hxR/TLz9lz.RK/fMlkxA5G", "CherylMJohnson@armyspy.com");
            userService.save(user);

            role = roleService.find("ROLE_USER");
            user = new User(role, "Christopher", "Woods", "Cralmonce", "$2a$10$iIFEUH6ORbNM7HSy7cpDcuPqHPQL4l9hxR/TLz9lz.RK/fMlkxA5G", "ChristopherCWoods@teleworm.us");
            userService.save(user);

        }
    }
}
