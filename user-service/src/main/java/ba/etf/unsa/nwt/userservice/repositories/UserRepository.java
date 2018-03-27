package ba.etf.unsa.nwt.userservice.repositories;

import ba.etf.unsa.nwt.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select count(u) from User u where u.username = :uname or u.email = :email")
    public int isUsernameOrEmailUnique(@Param("uname") String uname, @Param("email") String email);

    @Query("select count(u) from User u where u.email = :email")
    public int isEmailUnique(@Param("email") String email);

}
