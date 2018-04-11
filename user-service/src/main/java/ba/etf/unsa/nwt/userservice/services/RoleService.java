package ba.etf.unsa.nwt.userservice.services;

import ba.etf.unsa.nwt.userservice.models.Role;
import ba.etf.unsa.nwt.userservice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Role find(String name) {
        return roleRepository.findByName(name);
    }
}
