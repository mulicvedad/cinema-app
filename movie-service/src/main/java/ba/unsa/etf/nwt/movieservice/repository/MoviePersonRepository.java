package ba.unsa.etf.nwt.movieservice.repository;

import ba.unsa.etf.nwt.movieservice.model.MoviePerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviePersonRepository extends JpaRepository<MoviePerson, Long> {
    MoviePerson findByFirstNameAndLastName(String firstName, String lastName);
}
