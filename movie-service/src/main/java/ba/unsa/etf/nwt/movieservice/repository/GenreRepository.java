package ba.unsa.etf.nwt.movieservice.repository;

import ba.unsa.etf.nwt.movieservice.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
