package ba.unsa.etf.nwt.movieservice.repository;

import ba.unsa.etf.nwt.movieservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Transactional
    void deleteByUserId(Long id);
    List<Review> findByUserId(Long userId);
}
