package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.controllers.CinemaShowingController;
import ba.etf.unsa.nwt.cinemaservice.controllers.dto.CinemaShowingDTO;
import ba.etf.unsa.nwt.cinemaservice.exceptions.ServiceException;
import ba.etf.unsa.nwt.cinemaservice.models.*;
import ba.etf.unsa.nwt.cinemaservice.repositories.CinemaSeatRepository;
import ba.etf.unsa.nwt.cinemaservice.repositories.CinemaShowingRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.logging.Logger;

@Service
public class CinemaShowingService extends BaseService<CinemaShowing, CinemaShowingRepository> {

    @Autowired
    RoomService roomService;
    @Autowired
    ShowingTypeService showingTypeService;
    @Autowired
    CinemaSeatRepository cinemaSeatRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    @Qualifier("eurekaClient")
    private EurekaClient eurekaClient;
    @Autowired
    private TimetableService timetableService;
    public Collection<CinemaShowing> findUpcomingShowings() {
        return repo.findUpcoming();
    }

    public Collection<CinemaShowing> findByDate(Date date) {
        return repo.findAllByDate(date);
    }

    public Collection<CinemaShowing> findByDateAndMovie(Date date, Long movieId) {
        return repo.findByDateAndMovieId(date, movieId);
    }

    public void createCinemaShowing(CinemaShowingDTO cinemaShowingDTO) {

        String url;
        try {
            Application application = eurekaClient.getApplication("movie-service");
            InstanceInfo instanceInfo = application.getInstances().get(0);
            url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "movies/" + cinemaShowingDTO.movieId;
            Logger.getLogger(CinemaShowingController.class.toString()).info("URL " + url);
        } catch (Exception e) {
            throw new ServiceException("Movie service is not available");
        }
        try {
            restTemplate.getForEntity(url, Map.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new ServiceException("Movie with given id doesn't exist");
            else
                throw e;
        }

        if (cinemaShowingDTO.startDateTime.after(cinemaShowingDTO.endDateTime)
                || cinemaShowingDTO.startDateTime.before(new Date()))
            throw new ServiceException("Invalid dates and/or times");

        Optional<Room> room = roomService.get(cinemaShowingDTO.roomId);
        Optional<ShowingType> showingType = showingTypeService.get(cinemaShowingDTO.showingTypeId);

        if (!room.isPresent())
            throw new ServiceException("Requested room doesn't exist");

        if (!showingType.isPresent())
            throw new ServiceException("Requested showing type doesn't exist");

        Collection<CinemaShowing> cinemaShowings = repo.findAllByRoom(room.get());

        int numOverlapping = repo.numberOfOverlappingShowings(room.get(), cinemaShowingDTO.startDateTime,
                cinemaShowingDTO.endDateTime);

        if (numOverlapping > 0)
            throw new ServiceException("Cannot create cinema showing because requsted cinema room is taken in requested" +
                    "time interval");

        Timetable cinemaShowingTimeTable = new Timetable(cinemaShowingDTO.startDateTime, cinemaShowingDTO.endDateTime);
        timetableService.save(cinemaShowingTimeTable);
        repo.save(new CinemaShowing(cinemaShowingDTO.movieId, cinemaShowingTimeTable, showingType.get(), room.get()));
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
