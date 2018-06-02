package ba.etf.unsa.nwt.cinemaservice.controllers;

import ba.etf.unsa.nwt.cinemaservice.controllers.dto.CinemaShowingDTO;
import ba.etf.unsa.nwt.cinemaservice.exceptions.ServiceException;
import ba.etf.unsa.nwt.cinemaservice.models.*;
import ba.etf.unsa.nwt.cinemaservice.models.Error;
import ba.etf.unsa.nwt.cinemaservice.services.CinemaShowingService;
import ba.etf.unsa.nwt.cinemaservice.services.RoomService;
import ba.etf.unsa.nwt.cinemaservice.services.ShowingTypeService;
import ba.etf.unsa.nwt.cinemaservice.models.ErrorResponseWrapper;
import ba.etf.unsa.nwt.cinemaservice.services.*;
import ba.etf.unsa.nwt.cinemaservice.utils.ReportHelper;
import com.netflix.discovery.converters.Auto;
import javassist.tools.web.BadHttpRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity getAllCinemaShowings(@RequestParam(value = "date", required = false) String date, Pageable pageable)
            throws BadHttpRequest {
        if (date != null)
            try {
                Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                return ResponseEntity.ok(cinemaShowingService.findByDate(newDate, pageable));
            } catch (ParseException e) {
                return errorResponse(HttpStatus.BAD_REQUEST, "Parsing failure", "date", DATE_PARSING_ERROR);
            }
        return ResponseEntity.ok(cinemaShowingService.getAllByPage(pageable));
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
    public Page<CinemaShowing> upcoming(Pageable pageable) {
        return cinemaShowingService.findUpcomingShowings(pageable);
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

    @GetMapping("/{id}/all-seats")
    public Collection<CinemaSeat> getAllSeats(@PathVariable("id") Long id) {
        return cinemaShowingService.getAllShowingSeats(id);
    }
    @GetMapping("/{id}")
    public CinemaShowing getCinemaShowing(@PathVariable("id") Long id) {
        CinemaShowing f  = cinemaShowingService.getCinemaShowing(id);
        return f;
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> generateReport() {
        try {
            String filepath = cinemaShowingService.generateReport();
            return createResponse(filepath);
        } catch (Exception e) {
            return error(e);
        }
    }

    private ResponseEntity<byte[]> createResponse(String filepath) {
        FileInputStream fileStream;
        try {
            fileStream = new FileInputStream(new File(filepath));
            byte[] contents = IOUtils.toByteArray(fileStream);
            fileStream.close();
            ReportHelper.deleteReportFile(filepath);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
            return response;
        }
        catch (Exception e) {
            return error(e);
        }
    }

    @ResponseBody
    private ResponseEntity error(Exception e) {
        Map<String, Map<String, String>> responseBody = new HashMap<>();
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        responseBody.put("error", error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }


    @GetMapping("/search")
    public ResponseEntity getMoviesByTitleLike(@RequestParam("title") String title) {
        return ResponseEntity.ok().body( cinemaShowingService.getMoviesByTitleLike(title));
    }

}
