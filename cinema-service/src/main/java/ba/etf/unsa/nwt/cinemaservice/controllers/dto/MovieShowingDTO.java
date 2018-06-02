package ba.etf.unsa.nwt.cinemaservice.controllers.dto;

import ba.etf.unsa.nwt.cinemaservice.models.CinemaShowing;
import ba.etf.unsa.nwt.cinemaservice.models.Room;
import ba.etf.unsa.nwt.cinemaservice.models.ShowingType;
import ba.etf.unsa.nwt.cinemaservice.models.Timetable;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieShowingDTO {

    public Long movieId;
    public String movieTitle;
    public String posterPath;
    public ShowingType showingType;
    public Long roomId;
    public BigDecimal ticketPrice;
    public String showingTimes;

    public MovieShowingDTO() { }

    public MovieShowingDTO(CinemaShowing cinemaShowing) {
        this.movieId = cinemaShowing.getMovieId();
        this.movieTitle = cinemaShowing.getMovieTitle();
        this.posterPath = cinemaShowing.getPosterPath();
        this.showingType = cinemaShowing.getShowingType();
        this.roomId = cinemaShowing.getRoom().getId();
        this.ticketPrice = cinemaShowing.getTicketPrice();
    }
}
