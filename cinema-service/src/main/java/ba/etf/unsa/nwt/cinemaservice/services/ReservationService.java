package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.controllers.ReservationController;
import ba.etf.unsa.nwt.cinemaservice.controllers.dto.ReservationDTO;
import ba.etf.unsa.nwt.cinemaservice.controllers.dto.UserAccountDTO;
import ba.etf.unsa.nwt.cinemaservice.exceptions.ServiceException;
import ba.etf.unsa.nwt.cinemaservice.models.ChargeRequest;
import ba.etf.unsa.nwt.cinemaservice.models.CinemaSeat;
import ba.etf.unsa.nwt.cinemaservice.models.CinemaShowing;
import ba.etf.unsa.nwt.cinemaservice.models.Reservation;
import ba.etf.unsa.nwt.cinemaservice.repositories.ReservationRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.apache.http.auth.AUTH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.logging.Logger;

@Service
public class ReservationService extends BaseService<Reservation, ReservationRepository> {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("eurekaClient")
    private EurekaClient eurekaClient;

    @Autowired
    private ReservationStatusService reservationStatusService;

    @Autowired
    private CinemaShowingService cinemaShowingService;

    @Autowired
    private CinemaSeatService cinemaSeatService;

    @Autowired
    AuthService authService;

    private final String HEADER_NAME_AUTHORIZATION = "Authorization";
    private final String HEADER_PREFIX_AUTHORIZATION = "Bearer ";

    public Collection<Reservation> findByUserId(Long userId) {
        return repo.findByUserId(userId);
    }

    public void deleteReservationsByUser(Long id) {
        repo.deleteByUserId(id);
    }

    public void deleteReservationsByMovie(Long id) { repo.deleteReservationByCinemaShowingMovieId(id);}
  
    @Transactional
    public Reservation create(ReservationDTO reservationDTO) throws ServiceException {
        Long cinemaShowingId = reservationDTO.cinemaShowingId;
        Long userId = reservationDTO.userId;
        List<Long> seats = reservationDTO.seats;

        Logger.getLogger(ReservationController.class.toString()).info("CINEMA SHOWING ID: " + cinemaShowingId.toString());
        Logger.getLogger(ReservationController.class.toString()).info("USER ID: " + userId.toString());

        Optional<CinemaShowing> cinemaShowing = cinemaShowingService.get(cinemaShowingId);

        if (!cinemaShowing.isPresent())
            throw new ServiceException("Cinema showing with given id doesn't exist");

        Date dateNow = new Date();
        if (cinemaShowing.get().getTimetable().getStartDateTime().before(dateNow))
            throw new ServiceException("Reservation creation failed. Requested cinema showing has already been played.");

        String url = null;
        try {
            Application application = eurekaClient.getApplication("user-service");
            InstanceInfo instanceInfo = application.getInstances().get(0);
            url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "users/" + userId
                    + "/details";
            Logger.getLogger(ReservationController.class.toString()).info("URL " + url);
        } catch (Exception e) {
            throw new ServiceException("User service is not available");
        }

        try {
            // old version - doesn't work becauser JWT has to be passed in Authorization header
            // restTemplate.getForEntity(url, Map.class);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set(HEADER_NAME_AUTHORIZATION, HEADER_PREFIX_AUTHORIZATION + authService.getCurrentJwt());
            UserAccountDTO userAccount = new UserAccountDTO();
            HttpEntity<UserAccountDTO> entity = new HttpEntity<>(userAccount, headers);
            restTemplate.exchange(url, HttpMethod.GET, entity, UserAccountDTO.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new ServiceException("User with given id doesn't exist");
            else if (e.getStatusCode() == HttpStatus.FORBIDDEN)
                throw new ServiceException(("Not authorized to access users"));
            else
                throw e;
        }

        // check if there are duplicates in list of seats
        Set<Long> uniqueSeats = new HashSet<>(seats);
        if (uniqueSeats.size() != seats.size()) {
            throw new ServiceException("Reservation denied. There are duplicate seats in request");
        }

        List<CinemaSeat> cinemaSeats = new ArrayList<>();
        for (Long id : seats) {
            Optional<CinemaSeat> cinemaSeat = cinemaSeatService.get(id);
            if (!cinemaSeat.isPresent())
                throw new ServiceException("Seat with given id doesn't exist");
            int testCount = this.repo.countAllBySeatsContainsAndCinemaShowingAndStatusNot(cinemaSeat.get(), cinemaShowing.get(),
                    reservationStatusService.getStatusForNewReservation());
            Logger.getLogger(ReservationController.class.toString()).info("SEATS: " + testCount);
            // for every seat in current reservation check if seat is already taken in some other reservation
            // which is not denied (if it was denied then the seat is considered available)
            if (this.repo.countAllBySeatsContainsAndCinemaShowingAndStatusNot(cinemaSeat.get(), cinemaShowing.get(),
                    reservationStatusService.getStatusForDeniedReservation()) > 0)
                throw new ServiceException("Reservation couldn't be created. Seat with id = " + id + " is already taken");
            cinemaSeats.add(cinemaSeat.get());
        }

        try {
            Reservation r = this.save(new Reservation(cinemaShowing.get(), userId,
                    reservationStatusService.getStatusForNewReservation(), cinemaSeats));
            return r;
        } catch (Exception e) {
            Logger.getLogger(ReservationController.class.toString()).info("Onixpected exception in ReservationController.");
            throw new ServiceException("An error occured while saving new reservation.");
        }

    }

    public boolean payReservation(ChargeRequest chargeRequest, Long reservationId) {

        Optional<Reservation> reservation = repo.findById(reservationId);

        if (!reservation.isPresent())
            throw new ServiceException("Reservation not found");

        if (!reservation.get().getStatus().getStatusTitle().equals("new"))
            throw new ServiceException("Reservation status is not appropriate");

        String url;
        try {
            Application application = eurekaClient.getApplication("payment-service");
            InstanceInfo instanceInfo = application.getInstances().get(0);
            url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/charge";
            Logger.getLogger(ReservationController.class.toString()).info("URL " + url);
        } catch (Exception e) {
            throw new ServiceException("Payment service is not available");
        }


        try {
            ResponseEntity responseEntity = restTemplate.postForEntity(url, chargeRequest, ChargeRequest.class);
            if (responseEntity.getStatusCode() != HttpStatus.OK)
                return false;
            repo.updateStatus(reservationId, reservationStatusService.getStatusForConfirmedReservation());
        } catch (HttpClientErrorException e) {
            Logger.getLogger(ReservationController.class.toString()).info(e.getMessage());
            return false;
        }

        return true;
    }

}
