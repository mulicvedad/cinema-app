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
import javax.xml.ws.Service;
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

    private final String DATE_PARSING_ERROR = "Date/time parsing exception: Date must be in format 'yyyy-MM-dd'.";
    private final String TIME_PARSING_ERROR = "Time must be in format 'hh:mm'.";

    // using ISO 8601 date format
    @GetMapping
    public ResponseEntity getAllCinemaShowings(@RequestParam(value = "date", required = false) String date)
            throws BadHttpRequest {
        if (date != null)
            try {
                Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                return ResponseEntity.ok(cinemaShowingService.findByDate(newDate));
            } catch (ParseException e) {
                return errorResponse(HttpStatus.BAD_REQUEST, "Parsing failure", "date", DATE_PARSING_ERROR);
            }
        return ResponseEntity.ok(cinemaShowingService.all());
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity getAllCinemaShowingsForSpecificMovie(@RequestParam(value = "date", required = false) String date,
                                                               @PathVariable(name = "movieId") Long movieId)
            throws BadHttpRequest {
        if (date != null && movieId != null)
            try {
                Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                return ResponseEntity.ok(cinemaShowingService.findByDateAndMovie(newDate, movieId));
            } catch (ParseException e) {
                return errorResponse(HttpStatus.BAD_REQUEST, "Parsing failure", "date", DATE_PARSING_ERROR);
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
            return errorResponse(HttpStatus.BAD_REQUEST, "Cinema showing creation failed", null, e.getMessage());
        } catch (ParseException e) {
            return errorResponse(HttpStatus.BAD_REQUEST, "Cinema showing creation failed", null,
                    DATE_PARSING_ERROR + TIME_PARSING_ERROR);
        }
        return null;
    }

    @GetMapping("/{id}/available-seats")
    public Collection<CinemaSeat> getAvailableSeats(@PathVariable("id") Long id) {
        return cinemaShowingService.getAvailableSeats(id);
    }

    @GetMapping("/check-availability")
    public ResponseEntity checkAvailability(@RequestParam("room") Long roomId, @RequestParam("date") String date,
                                            @RequestParam("time") String time, @RequestParam("duration") Integer duration) {
        String errorMessage;
        try {
            if (cinemaShowingService.checkAvailability(roomId, date, time, duration)) {
                return ResponseEntity.ok().build();
            } else {
                errorMessage = "Requested room is not available in given time interval";
            }
        } catch (ParseException e) {
            errorMessage = DATE_PARSING_ERROR + TIME_PARSING_ERROR;
        } catch (ServiceException e) {
            errorMessage = e.getMessage();
        }
        return errorResponse(HttpStatus.BAD_REQUEST, "Bad request", null, errorMessage);
    }

    private ResponseEntity errorResponse(HttpStatus status, String title, String field, String message) {
        return ResponseEntity.status(status.value()).body(new ErrorResponseWrapper(new Error(title, field, message)));
    }
}
