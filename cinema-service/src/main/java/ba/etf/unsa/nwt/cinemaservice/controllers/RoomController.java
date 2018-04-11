package ba.etf.unsa.nwt.cinemaservice.controllers;

import ba.etf.unsa.nwt.cinemaservice.controllers.dto.RoomDTO;
import ba.etf.unsa.nwt.cinemaservice.models.Error;
import ba.etf.unsa.nwt.cinemaservice.models.ErrorResponseWrapper;
import ba.etf.unsa.nwt.cinemaservice.models.Room;
import ba.etf.unsa.nwt.cinemaservice.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity getRoom(@PathVariable("id") Long roomId) {
        Optional<Room> room = roomService.get(roomId);
        if (!room.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseWrapper(new Error("Not found",
                    "id", "Room with id = " + roomId + " doesn't exist")));
        return ResponseEntity.ok(room.get());
    }

    @PostMapping
    public ResponseEntity createRoom(@RequestBody @Valid RoomDTO roomDTO) {
        try {
            Room room = new Room(roomDTO.title, roomDTO.numSeats, roomDTO.numRows, roomDTO.numCols,
                    roomDTO.description);
            roomService.save(room);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper(new Error("unknown",
                    "unkown", "Creation of new room failed. Nested exception: \n" + e.getMessage())));
        }

        return ResponseEntity.ok().build();
    }
}
