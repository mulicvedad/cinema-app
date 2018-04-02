package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.models.ReservationStatus;
import ba.etf.unsa.nwt.cinemaservice.repositories.ReservationStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationStatusService extends BaseService<ReservationStatus, ReservationStatusRepository> {
    public ReservationStatus getStatusForNewReservation() {
        return repo.findByStatusTitle("new");
    }
    public ReservationStatus getStatusForConfirmedReservation() {
        return repo.findByStatusTitle("confirmed");
    }

}
