package ba.etf.unsa.nwt.cinemaservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "room")
public class Room extends BaseModel{

    private String title;
    private Integer numSeats;
    private String description;
    private Collection<CinemaSeat> seats;

    private Collection<CinemaShowing> cinemaShowings;

    protected Room() {}

    public Room(String title, Integer numSeats, String description) {
        this.title = title;
        this.numSeats = numSeats;
        this.description = description;
    }

    @NotBlank(message = "Table room: Column title cannot be blank nor null")
    @Size(max = 5000, message = "Table room: Column title cannot be longer than 30 characters")
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull(message = "Table room: Column number_of_seats cannot be null")
    @Column(name = "number_of_seats")
    public Integer getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(Integer numSeats) {
        this.numSeats = numSeats;
    }

    @Size(max = 255, message = "Table room: Column description cannot be longer than 255 characters")
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    public Collection<CinemaSeat> getSeats() {
        return seats;
    }

    public void setSeats(Collection<CinemaSeat> seats) {
        this.seats = seats;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    public Collection<CinemaShowing> getCinemaShowings() {
        return cinemaShowings;
    }

    public void setCinemaShowings(Collection<CinemaShowing> cinemaShowings) {
        this.cinemaShowings = cinemaShowings;
    }

}
