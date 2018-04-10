package ba.etf.unsa.nwt.cinemaservice.controllers;

import ba.etf.unsa.nwt.cinemaservice.models.CinemaShowing;
import ba.etf.unsa.nwt.cinemaservice.models.Error;
import ba.etf.unsa.nwt.cinemaservice.models.ErrorResponseWrapper;
import ba.etf.unsa.nwt.cinemaservice.services.CinemaShowingService;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

@RestController
@RequestMapping("/cinema-showings")
public class CinemaShowingController {

    @Autowired
    CinemaShowingService cinemaShowingService;

    // this method will be changed
    @GetMapping
    public ResponseEntity getAllCinemaShowings(@RequestParam(name = "date", required = false) String date)
            throws BadHttpRequest {
        if (date != null)
            try {
                Date newDate = new SimpleDateFormat("dd-mm-yyyy").parse(date);
                return ResponseEntity.ok(cinemaShowingService.findByDate(date));
            } catch (ParseException e) {
                Logger.getLogger(CinemaShowingService.class.toString()).info(" Inner exception message: \n"
                        + e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(new Error(
                        "Parsing failure","date","Date parsing exception: Date must be " +
                        "in format 'dd-mm-yyyy'.")));
            }
        return ResponseEntity.ok(cinemaShowingService.all());
    }

    @GetMapping("/upcoming")
    public Collection<CinemaShowing> upcoming() {
        return cinemaShowingService.findUpcomingShowings();
    }

}
