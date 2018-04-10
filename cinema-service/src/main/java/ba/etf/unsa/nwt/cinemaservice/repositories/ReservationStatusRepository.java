package ba.etf.unsa.nwt.cinemaservice.repositories;

import ba.etf.unsa.nwt.cinemaservice.models.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, Long> {
    ReservationStatus findByStatusTitle(String statusTitle);

    @Transactional
    @Modifying
    @Query(value = "update ReservationStatus rs set rs.statusTitle = :title where rs.id = :id")
    void updateStatusTitle(@Param("title") String title, @Param("id") Long id);
}
