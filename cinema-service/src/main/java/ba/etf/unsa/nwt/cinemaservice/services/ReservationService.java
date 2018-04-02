package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.models.Reservation;
import ba.etf.unsa.nwt.cinemaservice.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService extends BaseService<Reservation, ReservationRepository> {
    public void deleteReservationsByUser(Long id)
    {
        repo.deleteByUserId(id);
    }
}
