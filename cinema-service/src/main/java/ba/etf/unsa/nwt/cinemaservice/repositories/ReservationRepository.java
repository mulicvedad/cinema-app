package ba.etf.unsa.nwt.cinemaservice.repositories;

import ba.etf.unsa.nwt.cinemaservice.models.CinemaSeat;
import ba.etf.unsa.nwt.cinemaservice.models.CinemaShowing;
import ba.etf.unsa.nwt.cinemaservice.models.Reservation;
import ba.etf.unsa.nwt.cinemaservice.models.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Collection<Reservation> findByUserId(Long userId);

    @Transactional
    void deleteByUserId(Long id);

    @Transactional
    @Modifying
    void deleteReservationByCinemaShowingMovieId(Long id);

    @Query(value = "update reservation set status = (select id from status where title like :status) where id = :reservationId",
        nativeQuery = true)
    void updateStatus_old(Long reservationId, String status);

    @Transactional
    @Modifying
    @Query(value = "update Reservation r set r.status = :status where r.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") ReservationStatus status);

    int countAllBySeatsContainsAndCinemaShowingAndStatusNot(CinemaSeat cinemaSeat, CinemaShowing cinemaShowing, ReservationStatus reservationStatus);

}
