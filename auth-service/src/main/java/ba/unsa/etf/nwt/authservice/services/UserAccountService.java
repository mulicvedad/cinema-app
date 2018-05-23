package ba.unsa.etf.nwt.authservice.services;

import ba.unsa.etf.nwt.authservice.models.Role;
import ba.unsa.etf.nwt.authservice.models.UserAccount;
import ba.unsa.etf.nwt.authservice.repositories.RoleRepository;
import ba.unsa.etf.nwt.authservice.repositories.UserRepository;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Optional<UserAccount> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String getRoles(String username) {
        List<Role> userRoles = roleRepository.findAllByUserAccountsContains(this.findByUsername(username).get());
        String roles = "";
        for (Role role : userRoles)
            roles += role.toString();
        return roles;
    }

    public boolean isAdmin(String username) {
        return getRoles(username).contains("ROLE_ADMIN");
    }

    public Long count() {
        return this.userRepository.count();
    }

    public UserAccount save(UserAccount userAccount) {
        return this.userRepository.save(userAccount);
    }
}
