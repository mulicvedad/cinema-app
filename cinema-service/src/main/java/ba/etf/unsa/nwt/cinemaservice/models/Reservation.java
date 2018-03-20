package ba.etf.unsa.nwt.cinemaservice.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "reservation")
public class Reservation extends BaseModel{
    private CinemaShowing cinemaShowing;
    private Long userId;
    private Collection<CinemaSeat> seats;
    private ReservationStatus status;

    protected Reservation() {}

    public Reservation(CinemaShowing cinemaShowing, Long userId, Collection<CinemaSeat> seats) {
        this.cinemaShowing = cinemaShowing;
        this.userId = userId;
        this.seats = seats;
    }

    @ManyToOne
    @JoinColumn(name = "cinema_showing_id")
    public CinemaShowing getCinemaShowing() {
        return cinemaShowing;
    }

    public void setCinemaShowing(CinemaShowing cinemaShowing) {
        this.cinemaShowing = cinemaShowing;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @ManyToMany(
            targetEntity = ba.etf.unsa.nwt.cinemaservice.models.CinemaSeat.class
    )
    @JoinTable(
            name = "reservation_seat",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    public Collection<CinemaSeat> getSeats() {
        return seats;
    }

    public void setSeats(Collection<CinemaSeat> seats) {
        this.seats = seats;
    }

    @ManyToOne
    @JoinColumn(name = "reservation_status_id")
    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}

