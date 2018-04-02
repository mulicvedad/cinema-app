package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.controllers.ReservationController;
import ba.etf.unsa.nwt.cinemaservice.exceptions.ServiceException;
import ba.etf.unsa.nwt.cinemaservice.models.ChargeRequest;
import ba.etf.unsa.nwt.cinemaservice.models.ErrorResponse;
import ba.etf.unsa.nwt.cinemaservice.models.Reservation;
import ba.etf.unsa.nwt.cinemaservice.models.ReservationStatus;
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

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ReservationService extends BaseService<Reservation, ReservationRepository> {
    
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    @Qualifier("eurekaClient")
    EurekaClient eurekaClient;

    @Autowired
    ReservationStatusService reservationStatusService;

    public Collection<Reservation> findByUserId(Long userId) {
        return repo.findByUserId(userId);
    }

    public void deleteReservationsByUser(Long id) {
        repo.deleteByUserId(id);
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
