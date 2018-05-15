package ba.etf.unsa.nwt.cinemaservice.controllers;

import ba.etf.unsa.nwt.cinemaservice.controllers.dto.CinemaShowingDTO;
import ba.etf.unsa.nwt.cinemaservice.exceptions.ServiceException;
import ba.etf.unsa.nwt.cinemaservice.models.CinemaSeat;
import ba.etf.unsa.nwt.cinemaservice.models.CinemaShowing;
import ba.etf.unsa.nwt.cinemaservice.models.Error;
import ba.etf.unsa.nwt.cinemaservice.models.ErrorResponseWrapper;
import ba.etf.unsa.nwt.cinemaservice.services.CinemaShowingService;
import ba.etf.unsa.nwt.cinemaservice.services.RoomService;
import ba.etf.unsa.nwt.cinemaservice.services.ShowingTypeService;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("/cinema-showings")
public class CinemaShowingController {

    @Autowired
    CinemaShowingService cinemaShowingService;

    @Autowired
    RoomService roomService;

    @Autowired
    ShowingTypeService showingTypeService;

    // using ISO 8601 date format
    @GetMapping
    public ResponseEntity getAllCinemaShowings(@RequestParam(value = "date", required = false) String date)
            throws BadHttpRequest {
        if (date != null)
            try {
                Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                return ResponseEntity.ok(cinemaShowingService.findByDate(newDate));
            } catch (ParseException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(new Error(
                        "Parsing failure", "date", "Date parsing exception: Date must be " +
                        "in format 'yyyy-mm-dd'.")));
            }
        return ResponseEntity.ok(cinemaShowingService.all());
    }

    @GetMapping("/upcoming")
    public Collection<CinemaShowing> upcoming() {
        return cinemaShowingService.findUpcomingShowings();
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid CinemaShowingDTO cinemaShowingDTO) {
        try {
            cinemaShowingService.createCinemaShowing(cinemaShowingDTO);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseWrapper(new Error("Cinema showing creation failed",
                    null, e.getMessage())));
        }

        return null;
    }

    @GetMapping("/{id}/available-seats")
    public Collection<CinemaSeat> getAvailableSeats(@PathVariable("id") Long id) {
        return cinemaShowingService.getAvailableSeats(id);
    }
}
