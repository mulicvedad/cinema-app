package ba.etf.unsa.nwt.userservice.repositories;

import ba.etf.unsa.nwt.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
