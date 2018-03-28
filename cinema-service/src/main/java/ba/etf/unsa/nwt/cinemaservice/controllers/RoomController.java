package ba.etf.unsa.nwt.cinemaservice.controllers;

import ba.etf.unsa.nwt.cinemaservice.controllers.dto.RoomDTO;
import ba.etf.unsa.nwt.cinemaservice.models.ErrorResponse;
import ba.etf.unsa.nwt.cinemaservice.models.Room;
import ba.etf.unsa.nwt.cinemaservice.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    RoomService roomService;

    @GetMapping
    public Collection<Room> getAllRooms() {
        return roomService.all();
    }

    @GetMapping(value = "{id}/details", produces = "application/json")
    public ResponseEntity getRoom(@PathVariable("id") Long roomId) {
        Optional<Room> room = roomService.get(roomId);
        if(!room.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("id",
                    "Room with id = " + roomId.toString() + " doesn't exist"));
        return ResponseEntity.ok(room.get());
    }

    @PostMapping("/create")
    public ResponseEntity createRoom(@RequestBody RoomDTO roomDTO) {
        Room room = null;
        try {
            room = new Room(roomDTO.getTitle(), roomDTO.getNumSeats(), roomDTO.getDescription());
            roomService.save(room);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("unknown",
                    "Creation of new room failed. Nested exception: \n" + e.getMessage()));
        }

        return ResponseEntity.ok(room);
    }
}
