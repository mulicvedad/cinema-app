package ba.etf.unsa.nwt.cinemaservice.models;

import javax.persistence.*;
import java.text.CollationElementIterator;
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

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "number_of_seats")
    public Integer getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(Integer numSeats) {
        this.numSeats = numSeats;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "room")
    public Collection<CinemaSeat> getSeats() {
        return seats;
    }

    public void setSeats(Collection<CinemaSeat> seats) {
        this.seats = seats;
    }

    @OneToMany(mappedBy = "room")
    public Collection<CinemaShowing> getCinemaShowings() {
        return cinemaShowings;
    }

    public void setCinemaShowings(Collection<CinemaShowing> cinemaShowings) {
        this.cinemaShowings = cinemaShowings;
    }

}
