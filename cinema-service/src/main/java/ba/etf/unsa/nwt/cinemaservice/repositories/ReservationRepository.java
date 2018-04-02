package ba.etf.unsa.nwt.cinemaservice.repositories;

import ba.etf.unsa.nwt.cinemaservice.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    public Collection<Reservation> findByUserId(Long userId);
}
