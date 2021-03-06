package ba.etf.unsa.nwt.cinemaservice.controllers;

import ba.etf.unsa.nwt.cinemaservice.controllers.dto.ReservationDTO;
import ba.etf.unsa.nwt.cinemaservice.exceptions.ServiceException;
import ba.etf.unsa.nwt.cinemaservice.models.ChargeRequest;
import ba.etf.unsa.nwt.cinemaservice.models.Error;
import ba.etf.unsa.nwt.cinemaservice.models.ErrorResponseWrapper;
import ba.etf.unsa.nwt.cinemaservice.models.Reservation;
import ba.etf.unsa.nwt.cinemaservice.services.CinemaSeatService;
import ba.etf.unsa.nwt.cinemaservice.services.CinemaShowingService;
import ba.etf.unsa.nwt.cinemaservice.services.ReservationService;
import ba.etf.unsa.nwt.cinemaservice.services.ReservationStatusService;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    CinemaShowingService cinemaShowingService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    @Qualifier("eurekaClient")
    EurekaClient eurekaClient;

    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationStatusService reservationStatusService;

    @Autowired
    CinemaSeatService cinemaSeatService;

    @GetMapping("/{id}")
    ResponseEntity getReservationById(@PathVariable Long id) {
        if (reservationService.get(id).isPresent())
            return ResponseEntity.ok().body(reservationService.get(id).get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public Collection<Reservation> getAllReservations(@RequestParam(value = "userId", required = false) Long userId) {
        if (userId != null)
            return reservationService.findByUserId(userId);
        return reservationService.all();
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid ReservationDTO reservationInfo) {
        Reservation r = null;
        try {
             r = reservationService.create(reservationInfo);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(
                    new Error("Reservation failed", null, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(new Error("Reservation failed",
                    null, "An error occured during reservation creation.")));
        }

        return ResponseEntity.ok().body(r.getId());
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity payReservation(@RequestBody @Valid ChargeRequest chargeRequest, @PathVariable Long id) {
        boolean valid = false;
        String message = "Reservation payment not successful";
        try {
            valid = reservationService.payReservation(chargeRequest, id);
        } catch (ServiceException e) {
            valid = false;
            message += ": " + e.getMessage();
        } catch (Exception e) {
            valid = false;
            message += ": Something went wrong trying to process payment request. Error message: \n" + e.getMessage();
        }

        if (!valid)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(
                    new Error("none", "unknown", message)));

        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        String message = "Deleting reservation not successful";
        boolean valid = true;
        try {
            reservationService.deleteReservation(id);
        }
        catch (Exception e) {
            valid = false;
            message += ": " + e.getMessage();
        }
        if (!valid)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(
                    new Error("none", "unknown", message)));

        return ResponseEntity.ok().build();
    }


}
