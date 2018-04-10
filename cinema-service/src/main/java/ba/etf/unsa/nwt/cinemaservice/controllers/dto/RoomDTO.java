package ba.etf.unsa.nwt.cinemaservice.controllers.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class RoomDTO {

    @NotNull
    public String title;
    @Positive
    @Max(1000)
    public Integer numSeats;
    @Positive
    @Max(20)
    public Integer numRows;
    @Positive
    @Max(20)
    public Integer numCols;
    public String description;

    protected RoomDTO() {}

    public RoomDTO(String title, Integer numSeats, Integer numRows, Integer numCols, String description) {
        this.title = title;
        this.numSeats = numSeats;
        this.numRows = numRows;
        this.numCols = numCols;
        this.description = description;
    }
}


