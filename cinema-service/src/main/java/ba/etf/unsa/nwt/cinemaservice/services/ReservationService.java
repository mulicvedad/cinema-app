package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.controllers.ReservationController;
import ba.etf.unsa.nwt.cinemaservice.controllers.dto.ReservationDTO;
import ba.etf.unsa.nwt.cinemaservice.exceptions.ServiceException;
import ba.etf.unsa.nwt.cinemaservice.models.*;
import ba.etf.unsa.nwt.cinemaservice.repositories.ReservationRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.converters.Auto;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.logging.Logger;

import java.util.Collection;

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

    public Collection<Reservation> findByUserId(Long userId) {
        return repo.findByUserId(userId);
    }

    public void deleteReservationsByUser(Long id) {
        repo.deleteByUserId(id);
    }

    public void create(ReservationDTO reservationDTO) {
        Long cinemaShowingId = reservationDTO.cinemaShowingId;
        Long userId = reservationDTO.userId;
        List<Long> seats = reservationDTO.seats;

        Logger.getLogger(ReservationController.class.toString()).info("CINEMA SHOWING ID: " + cinemaShowingId.toString());
        Logger.getLogger(ReservationController.class.toString()).info("USER ID: " + userId.toString());

        Optional<CinemaShowing> cinemaShowing = cinemaShowingService.get(cinemaShowingId);

        if (!cinemaShowing.isPresent())
           throw new ServiceException("Cinema showing with given id doesn't exist");

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
            restTemplate.getForEntity(url, Map.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
               throw new ServiceException("User with given id doesn't exist");
            else
                throw e;
        }


        List<CinemaSeat> cinemaSeats = new ArrayList<>();
        for(Long id : seats) {
            Optional<CinemaSeat> cinemaSeat = cinemaSeatService.get(id);
            if (!cinemaSeat.isPresent())
                throw new ServiceException("Seat with given id doesn't exist");
            cinemaSeats.add(cinemaSeat.get());
        }

        try {
            this.save(new Reservation(cinemaShowing.get(), userId,
                    reservationStatusService.getStatusForNewReservation(), cinemaSeats));
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

        Application application = eurekaClient.getApplication("payment-service");
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/charge";
        Logger.getLogger(ReservationController.class.toString()).info("URL " + url);

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
