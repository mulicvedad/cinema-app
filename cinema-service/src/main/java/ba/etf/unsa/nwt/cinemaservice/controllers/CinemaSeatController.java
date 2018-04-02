package ba.etf.unsa.nwt.cinemaservice.controllers;

import ba.etf.unsa.nwt.cinemaservice.controllers.dto.CinemaSeatDTO;
import ba.etf.unsa.nwt.cinemaservice.exceptions.ModelNotFoundException;
import ba.etf.unsa.nwt.cinemaservice.models.CinemaSeat;
import ba.etf.unsa.nwt.cinemaservice.models.ErrorResponse;
import ba.etf.unsa.nwt.cinemaservice.services.CinemaSeatService;
import ba.etf.unsa.nwt.cinemaservice.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/cinema_seats")
public class CinemaSeatController {

    @Autowired
    private CinemaSeatService cinemaSeatService;

    @Autowired
    private RoomService roomService;

    @GetMapping
    public Collection<CinemaSeat> getAllCinemaSeats() {
        return cinemaSeatService.all();
    }

    @GetMapping(value = "{id}/details", produces = "application/json")
    public ResponseEntity getCinemaSeat(@PathVariable("id") Long cinemaSeatId) {
        Optional<CinemaSeat> cinemaSeat = cinemaSeatService.get(cinemaSeatId);
        if(!cinemaSeat.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("id",
                    "Cinema seat with id = " + cinemaSeatId.toString() + " doesn't exist"));
        return ResponseEntity.ok(cinemaSeat.get());
    }

    @PostMapping("/create")
    public ResponseEntity createCinemaSeat(@RequestBody CinemaSeatDTO cinemaSeatDTO) {
        if (!roomService.get(cinemaSeatDTO.getRoomId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("id",
                    "Cinema room with id = " + cinemaSeatDTO.getRoomId().toString() + " doesn't exist"));
        }

        CinemaSeat cinemaSeat = null;
        try {
            cinemaSeat = new CinemaSeat(roomService.get(cinemaSeatDTO.getRoomId()).get(),
                    cinemaSeatDTO.getRowNum(), cinemaSeatDTO.getColNum(), cinemaSeatDTO.getMark());
            cinemaSeatService.save(cinemaSeat);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("unknown",
                    "Creation of new cinema seat failed. Nested exception: " + e.getMessage()));
        }

        return ResponseEntity.ok(cinemaSeat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long cinemaSeatId) {
        Optional<CinemaSeat> cinemaSeat = cinemaSeatService.get(cinemaSeatId);
        if (!cinemaSeat.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("id",
                    "Unable to delete cinema seat with id = " + cinemaSeatId.toString()
                            + " because such object doesn't exist"));
        try {
            cinemaSeatService.delete(cinemaSeat.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(new ErrorResponse("unknown",
                    "Deletion of the cinema seat failed"));

        }

        return ResponseEntity.ok().build();
    }

}
