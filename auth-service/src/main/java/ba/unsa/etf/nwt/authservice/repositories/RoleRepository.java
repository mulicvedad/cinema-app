package ba.unsa.etf.nwt.authservice.repositories;

import ba.unsa.etf.nwt.authservice.models.Role;
import ba.unsa.etf.nwt.authservice.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    List<Role> findAllByUserAccountsContains(UserAccount userAccount);
}
