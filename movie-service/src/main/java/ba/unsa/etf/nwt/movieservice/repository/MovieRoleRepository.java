package ba.unsa.etf.nwt.movieservice.repository;

import ba.unsa.etf.nwt.movieservice.model.MovieRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRoleRepository extends JpaRepository<MovieRole, Long> {
    MovieRole findByRole(String role);
}
