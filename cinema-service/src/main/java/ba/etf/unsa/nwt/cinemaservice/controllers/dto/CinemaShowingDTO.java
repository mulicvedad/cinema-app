package ba.etf.unsa.nwt.cinemaservice.controllers.dto;

import ba.etf.unsa.nwt.cinemaservice.models.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

public class CinemaShowingDTO {

    @NotNull
    @Positive
    public Long movieId;

    public String movieTitle;

    public String posterPath;

    @NotNull
    public String startDate;

    @NotNull
    public String startTime;

    @NotNull
    @Positive
    public Long roomId;

    @NotNull
    public Integer duration;

    public String showingTimes;

    protected CinemaShowingDTO() {}

    public CinemaShowingDTO(Long movieId, String movieTitle, String posterPath, String startDate, String startTime,
                            Long roomId, Integer duration, String showingTimes) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.posterPath = posterPath;
        this.startDate = startDate;
        this.startTime = startTime;
        this.roomId = roomId;
        this.duration = duration;
        this.showingTimes = showingTimes;
    }

}
