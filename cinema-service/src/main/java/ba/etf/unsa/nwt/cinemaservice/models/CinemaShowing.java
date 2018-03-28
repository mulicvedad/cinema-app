package ba.etf.unsa.nwt.cinemaservice.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table(name = "cinema_showing")
public class CinemaShowing extends BaseModel{
    private Long movieId;
    private Timetable timetable;
    private ShowingType showingType;
    private Room room;
    private Collection<Reservation> reservations;

    protected CinemaShowing() {}

    public CinemaShowing(Long movieId, Timetable timetable, ShowingType showingType, Room room) {
        this.movieId = movieId;
        this.timetable = timetable;
        this.showingType = showingType;
        this.room = room;
    }

    @Column(name = "movie_id")
    // @NotNull
    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    @NotNull(message = "Table cinema_showing: Column timetable_id cannot be null")
    @ManyToOne
    @JoinColumn(name = "timetable_id")
    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    @NotNull(message = "Table cinema_showing: Column showing_type_id cannot be null")
    @ManyToOne
    @JoinColumn(name = "showing_type_id")
    public ShowingType getShowingType() {
        return showingType;
    }

    public void setShowingType(ShowingType showingType) {
        this.showingType = showingType;
    }

    @NotNull(message = "Table cinema_showing: Column room_id cannot be null")
    @ManyToOne
    @JoinColumn(name = "room_id")
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @OneToMany(mappedBy = "cinemaShowing")
    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }
}
