package ba.etf.unsa.nwt.cinemaservice.repositories;

import ba.etf.unsa.nwt.cinemaservice.models.CinemaShowing;
import ba.etf.unsa.nwt.cinemaservice.models.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ba.etf.unsa.nwt.cinemaservice.models.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface CinemaShowingRepository extends JpaRepository<CinemaShowing, Long>{

    CinemaShowing findCinemaShowingById(Long id);

    @Query("select cs from CinemaShowing cs where cs.timetable.startDateTime >= current_date ")
    Page<CinemaShowing> findUpcoming(Pageable pageable);

    @Query("select  cs from CinemaShowing cs, Timetable t where  cs.timetable = t " +
            "and function('YEAR', t.startDateTime) = function('YEAR', :date) " +
            "and function('MONTH', t.startDateTime) = function('MONTH', :date) " +
            "and function('DAY', t.startDateTime) = function('DAY', :date) ")
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

    @Query("select  cs from CinemaShowing cs, Timetable t where  cs.timetable = t " +
            "and function('YEAR', t.startDateTime) = function('YEAR', :date) " +
            "and function('MONTH', t.startDateTime) = function('MONTH', :date) " +
            "and function('DAY', t.startDateTime) = function('DAY', :date) " +
            "and cs.movieId = :movieId")
    List<CinemaShowing> findAllByDateAndMovieId(@Param("date") Date date, @Param("movieId") Long movieId);

    // find upcoming v2 (didn't see there was already method for upcoming showings...)
    @Query("select cs from CinemaShowing cs, Timetable t where cs.timetable = t and t.startDateTime > :date" +
            " order by t.startDateTime asc")
    List<CinemaShowing> customFindAllAfterDate(@Param("date") Date date);


    @Query("select cs from CinemaShowing  cs where UPPER(cs.movieTitle) LIKE CONCAT('%',UPPER(:movieTitle),'%')")
    List<CinemaShowing> getMoviesByTitleLike(@Param("movieTitle") String movieTitle);

}
