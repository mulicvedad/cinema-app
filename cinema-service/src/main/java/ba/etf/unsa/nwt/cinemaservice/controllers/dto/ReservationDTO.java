package ba.etf.unsa.nwt.cinemaservice.controllers.dto;

import java.util.List;

public class ReservationDTO {
    public Long cinemaShowingId;
    public Long userId;
    public List<Long> seats;

    protected ReservationDTO() {}

    public ReservationDTO(Long cinemaShowingId, Long userId, List<Long> seats) {
        this.cinemaShowingId = cinemaShowingId;
        this.userId = userId;
        this.seats = seats;
    }
}
