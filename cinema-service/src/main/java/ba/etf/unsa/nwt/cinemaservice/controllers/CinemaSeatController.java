package ba.etf.unsa.nwt.cinemaservice.controllers;

import ba.etf.unsa.nwt.cinemaservice.controllers.dto.CinemaSeatDTO;
import ba.etf.unsa.nwt.cinemaservice.models.CinemaSeat;
import ba.etf.unsa.nwt.cinemaservice.models.Error;
import ba.etf.unsa.nwt.cinemaservice.models.ErrorResponseWrapper;
import ba.etf.unsa.nwt.cinemaservice.services.CinemaSeatService;
import ba.etf.unsa.nwt.cinemaservice.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/cinema-seats")
public class CinemaSeatController {

    @Autowired
    private CinemaSeatService cinemaSeatService;

    @Autowired
    private RoomService roomService;

    @GetMapping
    public Collection<CinemaSeat> getAllCinemaSeats() {
        return cinemaSeatService.all();
    }

    @GetMapping("/{id}")
    public ResponseEntity getCinemaSeat(@PathVariable("id") Long cinemaSeatId) {
        Optional<CinemaSeat> cinemaSeat = cinemaSeatService.get(cinemaSeatId);
        if(!cinemaSeat.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseWrapper(new Error("Not found",
                    "id", "Cinema seat with id = " + cinemaSeatId + " doesn't exist")));
        return ResponseEntity.ok(cinemaSeat.get());
    }

    @PostMapping
    public ResponseEntity createCinemaSeat(@RequestBody CinemaSeatDTO cinemaSeatDTO) {
        if (!roomService.get(cinemaSeatDTO.roomId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseWrapper(new Error("Not found",
                    "id", "Cinema room with id = " + cinemaSeatDTO.roomId + " doesn't exist")));
        }

        CinemaSeat cinemaSeat = null;
        try {
            cinemaSeat = new CinemaSeat(roomService.get(cinemaSeatDTO.roomId).get(),
                    cinemaSeatDTO.rowNum, cinemaSeatDTO.colNum, cinemaSeatDTO.mark);
            cinemaSeatService.save(cinemaSeat);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(new Error("unknown",
                    "id", "Creation of new cinema seat failed. Nested exception: " + e.getMessage())));
        }

        return ResponseEntity.ok(cinemaSeat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long cinemaSeatId) {
        Optional<CinemaSeat> cinemaSeat = cinemaSeatService.get(cinemaSeatId);
        if (!cinemaSeat.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseWrapper(new Error("id",
                    "id", "Unable to delete cinema seat with id = " + cinemaSeatId.toString()
                            + " because such object doesn't exist")));
        try {
            cinemaSeatService.delete(cinemaSeat.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(new ErrorResponseWrapper(
                    new Error("unknown","none", "Deletion of the cinema seat failed")));

        }

        return ResponseEntity.ok().build();
    }

}
