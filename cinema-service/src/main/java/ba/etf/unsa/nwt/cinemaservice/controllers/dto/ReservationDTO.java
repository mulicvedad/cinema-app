package ba.etf.unsa.nwt.cinemaservice.controllers.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

public class ReservationDTO {

    @Positive
    public Long cinemaShowingId;
    @Positive
    public Long userId;
    @NotNull
    public List<Long> seats;

    protected ReservationDTO() {}

    public ReservationDTO(Long cinemaShowingId, Long userId, List<Long> seats) {
        this.cinemaShowingId = cinemaShowingId;
        this.userId = userId;
        this.seats = seats;
    }
}
