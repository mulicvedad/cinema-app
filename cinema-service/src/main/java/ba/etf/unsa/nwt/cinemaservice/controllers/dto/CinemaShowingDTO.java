package ba.etf.unsa.nwt.cinemaservice.controllers.dto;

import ba.etf.unsa.nwt.cinemaservice.models.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

public class CinemaShowingDTO {

    public String movieTitle;

    @NotNull
    public Date startDateTime;

    @NotNull
    public Date endDateTime;

    @Positive
    public Long showingTypeId;

    @NotNull
    @Positive
    public Long roomId;

    @NotNull
    @Positive
    public Long movieId;

    protected CinemaShowingDTO() {}

    public CinemaShowingDTO(Long movieId, String movieTitle, Date startDateTime, Date endDateTime, Long showingTypeId, Long roomId) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.showingTypeId = showingTypeId;
        this.roomId = roomId;
    }
}
