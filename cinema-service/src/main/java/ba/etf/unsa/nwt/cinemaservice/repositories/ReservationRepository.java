package ba.etf.unsa.nwt.cinemaservice.repositories;

import ba.etf.unsa.nwt.cinemaservice.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

import javax.transaction.Transactional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    public Collection<Reservation> findByUserId(Long userId);
    @Transactional
    public void deleteByUserId(Long id);
}
