package ba.unsa.etf.nwt.authservice.services;

import ba.unsa.etf.nwt.authservice.models.Role;
import ba.unsa.etf.nwt.authservice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public void save(Role role) {
        roleRepository.save(role);
    }

    public long count() {
        return roleRepository.count();
    }

    public Optional<Role> find(String name) {
        return roleRepository.findByName(name);
    }
}
