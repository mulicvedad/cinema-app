package ba.unsa.etf.nwt.movieservice.repository;

import ba.unsa.etf.nwt.movieservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
