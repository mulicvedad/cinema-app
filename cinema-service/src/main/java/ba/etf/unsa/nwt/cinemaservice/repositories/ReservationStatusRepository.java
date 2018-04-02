package ba.etf.unsa.nwt.cinemaservice.repositories;

import ba.etf.unsa.nwt.cinemaservice.models.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, Long> {
    ReservationStatus findByStatusTitle(String statusTitle);
}
