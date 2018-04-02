package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.models.CinemaShowing;
import ba.etf.unsa.nwt.cinemaservice.repositories.CinemaShowingRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class CinemaShowingService extends BaseService<CinemaShowing, CinemaShowingRepository> {
    public Collection<CinemaShowing> findUpcomingShowings() {
        return repo.findUpcoming();
    }

    public Collection<CinemaShowing> findByDate(String date) {
        return repo.findAllByDate(date);
    }
}
