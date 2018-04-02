package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.models.Reservation;
import ba.etf.unsa.nwt.cinemaservice.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ReservationService extends BaseService<Reservation, ReservationRepository> {
    public Collection<Reservation> findByUserId(Long userId) {
        return repo.findByUserId(userId);
    }
}
