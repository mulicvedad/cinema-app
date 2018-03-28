package ba.etf.unsa.nwt.paymentservice.repository;

import ba.etf.unsa.nwt.paymentservice.domain.ChargeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Adna Karkelja on 3/19/18.
 *
 * @author Adna Karkelja
 */
@Repository
public interface ChargeRequestRepository extends JpaRepository<ChargeRequest, Long> {
}