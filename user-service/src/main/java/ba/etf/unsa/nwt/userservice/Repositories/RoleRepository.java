package ba.etf.unsa.nwt.userservice.Repositories;

import ba.etf.unsa.nwt.userservice.Models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
