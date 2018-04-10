package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.models.CinemaSeat;
import ba.etf.unsa.nwt.cinemaservice.models.CinemaShowing;
import ba.etf.unsa.nwt.cinemaservice.repositories.CinemaSeatRepository;
import ba.etf.unsa.nwt.cinemaservice.repositories.CinemaShowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class CinemaShowingService extends BaseService<CinemaShowing, CinemaShowingRepository> {
    @Autowired
    CinemaSeatRepository cinemaSeatRepository;

    public Collection<CinemaShowing> findUpcomingShowings() {
        return repo.findUpcoming();
    }

    public Collection<CinemaShowing> findByDate(String date) {
        return repo.findAllByDate(date);
    }

    public Collection<CinemaSeat> getAvailableSeats(Long id) {

        Optional<CinemaShowing> cinemaShowing = repo.findById(id);
        Collection<CinemaSeat> reservedSeats = cinemaSeatRepository.findReservedSeats(id);
        Collection<CinemaSeat> allSeats = cinemaSeatRepository.findAllByRoom(cinemaShowing.get().getRoom());
        Collection<CinemaSeat> availableSeats = new ArrayList<>();
        for (CinemaSeat cinemaSeat : allSeats) {
            if (!reservedSeats.contains(cinemaSeat))
                availableSeats.add(cinemaSeat);
        }
        return availableSeats;
    }
}
