package ba.unsa.etf.nwt.movieservice.repository;

import ba.unsa.etf.nwt.movieservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Transactional
    public void deleteByUserId(Long id);
}
