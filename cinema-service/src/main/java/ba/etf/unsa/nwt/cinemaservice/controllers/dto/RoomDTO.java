package ba.etf.unsa.nwt.cinemaservice.controllers.dto;

public class RoomDTO {

    private String title;
    private Integer numSeats;
    private String description;

    protected RoomDTO() {}

    public RoomDTO(String title, Integer numSeats, String description) {
        this.title = title;
        this.numSeats = numSeats;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(Integer numSeats) {
        this.numSeats = numSeats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


