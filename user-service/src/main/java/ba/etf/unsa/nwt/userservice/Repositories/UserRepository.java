package ba.etf.unsa.nwt.userservice.Repositories;

import ba.etf.unsa.nwt.userservice.Models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
