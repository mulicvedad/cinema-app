package ba.etf.unsa.nwt.cinemaservice.repositories;

import ba.etf.unsa.nwt.cinemaservice.models.CinemaShowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;

public interface CinemaShowingRepository extends JpaRepository<CinemaShowing, Long>{
    @Query("select cs from CinemaShowing cs where cs.timetable.startDateTime >= current_date ")
    Collection<CinemaShowing> findUpcoming();

    @Query(value = "select * from cinema_showing cs, timetable tt where cs.timetable_id = tt.id" +
            " and DATE_FORMAT(tt.start_datetime, '%d-%m-%Y') = :startDate", nativeQuery = true)
    Collection<CinemaShowing> findAllByDate(@Param("startDate") String startDate);

    @Query("select cs from CinemaShowing cs where function('DATE',cs.timetable.startDateTime) = function('DATE', :startDate)")
    Collection<CinemaShowing> V2(@Param("startDate") String startDate);
}
