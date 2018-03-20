package ba.etf.unsa.nwt.cinemaservice.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "reservation_status")
public class ReservationStatus extends BaseModel {
    private String statusTitle;
    private Collection<Reservation> reservations;

    protected ReservationStatus() {}

    public ReservationStatus(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    @Column(name = "status_title")
    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    @OneToMany(mappedBy = "status")
    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }
}
