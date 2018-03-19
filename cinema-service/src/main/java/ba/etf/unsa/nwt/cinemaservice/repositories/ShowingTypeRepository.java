package ba.etf.unsa.nwt.cinemaservice.repositories;

import ba.etf.unsa.nwt.cinemaservice.models.ShowingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowingTypeRepository extends JpaRepository<ShowingType, Long> {
}
