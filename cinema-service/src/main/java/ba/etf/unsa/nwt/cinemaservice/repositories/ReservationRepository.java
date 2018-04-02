package ba.etf.unsa.nwt.cinemaservice.repositories;

import ba.etf.unsa.nwt.cinemaservice.models.Reservation;
import ba.etf.unsa.nwt.cinemaservice.models.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

import javax.transaction.Transactional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Collection<Reservation> findByUserId(Long userId);

    @Transactional
    void deleteByUserId(Long id);

    @Query(value = "update reservation set status = (select id from status where title like :status) where id = :reservationId",
        nativeQuery = true)
    void updateStatus_old(Long reservationId, String status);

    @Query(value = "update Reservation r set r.status = :status where r.id = :id")
    void updateStatus(Long reservationId, ReservationStatus status);

}
