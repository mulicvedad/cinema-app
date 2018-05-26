package ba.etf.unsa.nwt.cinemaservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = "cinema_showing")
public class CinemaShowing extends BaseModel{

    private Long movieId;
    private String movieTitle;
    private String posterPath;
    private Timetable timetable;
    private ShowingType showingType;
    private Room room;
    private BigDecimal ticketPrice;
    private Collection<Reservation> reservations;

    protected CinemaShowing() {}

    public CinemaShowing(Long movieId, String movieTitle, String posterPath, Timetable timetable, ShowingType showingType, Room room, BigDecimal ticketPrice) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.posterPath = posterPath;
        this.timetable = timetable;
        this.showingType = showingType;
        this.room = room;
        this.ticketPrice = ticketPrice;
    }

    public CinemaShowing(Long movieId, Timetable timetable, ShowingType showingType, Room room) {
        this.movieId = movieId;
        this.timetable = timetable;
        this.showingType = showingType;
        this.room = room;
    }

    @Column(name = "movie_id")
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

    @Column(name = "ticket_price")
    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "cinemaShowing")
    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Column(name="movie_title")
    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    @Column(name="poster_path")
    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
