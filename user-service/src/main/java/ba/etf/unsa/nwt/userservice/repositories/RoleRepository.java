package ba.etf.unsa.nwt.userservice.repositories;

import ba.etf.unsa.nwt.userservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
