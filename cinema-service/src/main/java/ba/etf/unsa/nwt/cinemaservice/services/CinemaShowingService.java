package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.controllers.dto.CinemaShowingDTO;
import ba.etf.unsa.nwt.cinemaservice.exceptions.ServiceException;
import ba.etf.unsa.nwt.cinemaservice.models.*;
import ba.etf.unsa.nwt.cinemaservice.models.Error;
import ba.etf.unsa.nwt.cinemaservice.repositories.CinemaShowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class CinemaShowingService extends BaseService<CinemaShowing, CinemaShowingRepository> {

    @Autowired
    RoomService roomService;

    @Autowired
    ShowingTypeService showingTypeService;

    public Collection<CinemaShowing> findUpcomingShowings() {
        return repo.findUpcoming();
    }

    public Collection<CinemaShowing> findByDate(Date date) {
        return repo.findAllByDate(date);
    }

    public void createCinemaShowing(CinemaShowingDTO cinemaShowingDTO) {

        if (cinemaShowingDTO.startDateTime.after(cinemaShowingDTO.endDateTime)
                || cinemaShowingDTO.startDateTime.before(new Date()))
            throw new ServiceException("Invalid dates and/or times");

        Optional<Room> room = roomService.get(cinemaShowingDTO.roomId);
        Optional<ShowingType> showingType = showingTypeService.get(cinemaShowingDTO.showingTypeId);

        if (!room.isPresent())
            throw new ServiceException("Requested room doesn't exist");

        if(!showingType.isPresent())
            throw new ServiceException("Requested showing type doesn't exist");

        Collection<CinemaShowing> cinemaShowings = repo.findAllByRoom(room.get());

        int numOverlapping = repo.numberOfOverlappingShowings(room.get(), cinemaShowingDTO.startDateTime,
                cinemaShowingDTO.endDateTime);

        if (numOverlapping > 0)
            throw new ServiceException("Cannot create cinema showing because requsted cinema room is taken in requested" +
                    "time interval");

        repo.save(new CinemaShowing(cinemaShowingDTO.movieId, new Timetable(cinemaShowingDTO.startDateTime,
                cinemaShowingDTO.endDateTime), showingType.get(), room.get()));
    }
}
