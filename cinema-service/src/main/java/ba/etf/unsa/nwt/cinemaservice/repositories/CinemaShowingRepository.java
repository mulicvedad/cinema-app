package ba.etf.unsa.nwt.cinemaservice.repositories;

import ba.etf.unsa.nwt.cinemaservice.models.CinemaShowing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaShowingRepository extends JpaRepository<CinemaShowing, Long>{
}
