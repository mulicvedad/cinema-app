package ba.etf.unsa.nwt.cinemaservice.repositories;

import ba.etf.unsa.nwt.cinemaservice.models.CinemaShowing;
import ba.etf.unsa.nwt.cinemaservice.models.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;

public interface CinemaShowingRepository extends JpaRepository<CinemaShowing, Long>{

    @Query("select cs from CinemaShowing cs where cs.timetable.startDateTime >= current_date ")
    Page<CinemaShowing> findUpcoming(Pageable pageable);

    @Query(value = "select * from cinema_showing cs, timetable tt where cs.timetable_id = tt.id" +
            " and DATE_FORMAT(tt.start_datetime, '%d-%m-%Y') = :startDate", nativeQuery = true)
    Collection<CinemaShowing> findAllByDateNative(@Param("startDate") Date startDate);

    // @Query("select cs from CinemaShowing cs where cs.timetable.startDate > :startDate")
    // Collection<CinemaShowing> findAllByDate(@Param("startDate") Date startDate);

    @Query("select  cs from CinemaShowing cs, Timetable t where  cs.timetable = t and t.startDateTime > :date")
    Page<CinemaShowing> findAllByDate(@Param("date") Date date, Pageable pageable);

    @Query("select count(cs) from CinemaShowing cs, Timetable t where cs.timetable =  t and " +
            "cs.room = :room and " +
            "(:startDateTime between t.startDateTime and t.endDateTime or " +
            ":endDateTime between t.startDateTime and t.endDateTime)")
    int numberOfOverlappingShowings(@Param("room") Room room, @Param("startDateTime") Date startDateTime,
                                    @Param("endDateTime") Date endDateTime);

    Collection<CinemaShowing> findAllByRoom(Room room);

    @Query("select  cs from CinemaShowing cs, Timetable t where  cs.timetable = t and cs.movieId = :movieId and t.startDateTime > :date")
    Collection<CinemaShowing> findByDateAndMovieId(@Param("date") Date date, @Param("movieId") Long movieId);
}
