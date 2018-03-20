package ba.etf.unsa.nwt.cinemaservice.repositories;

import ba.etf.unsa.nwt.cinemaservice.models.CinemaSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaSeatRepository extends JpaRepository<CinemaSeat, Long> {
}
