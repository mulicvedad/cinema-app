package ba.etf.unsa.nwt.cinemaservice.repositories;

import ba.etf.unsa.nwt.cinemaservice.models.CinemaSeat;
import ba.etf.unsa.nwt.cinemaservice.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface CinemaSeatRepository extends JpaRepository<CinemaSeat, Long> {
    @Query("SELECT seat FROM CinemaShowing cs, CinemaSeat seat " +
            "WHERE cs.id =:id AND seat IN " +
            "(SELECT c_seat FROM Reservation r, CinemaSeat c_seat " +
            "JOIN r.seats c_seat WHERE r.cinemaShowing = cs)")
    Collection<CinemaSeat> findReservedSeats(@Param("id") Long id);

    Collection<CinemaSeat> findAllByRoom(Room room);

}
