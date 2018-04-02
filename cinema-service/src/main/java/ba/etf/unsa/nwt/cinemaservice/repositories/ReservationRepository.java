package ba.etf.unsa.nwt.cinemaservice.repositories;

import ba.etf.unsa.nwt.cinemaservice.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Transactional
    public void deleteByUserId(Long id);
}
