package ba.etf.unsa.nwt.cinemaservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "Table reservation_status: Column status_title cannot be blank nor null")
    @Size(max = 30, message = "Table reservation_status: Column status_title cannot be longer than 30 characters")
    @Column(name = "status_title")
    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "status")
    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }

}
