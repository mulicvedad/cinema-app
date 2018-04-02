package ba.etf.unsa.nwt.cinemaservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "cinema_seat")
public class CinemaSeat extends BaseModel {

    private Room room;
    private Integer rowNum;
    private Integer colNum;
    private String mark;
    private Collection<Reservation> reservations;

    protected CinemaSeat() {}

    public CinemaSeat(Room room, Integer rowNum, Integer colNum, String mark) {
        this.room = room;
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.mark = mark;
    }

    @ManyToOne
    @JoinColumn(name = "room_id")
    @NotNull(message = "Table cinema_seat: Column room_id cannot be null")
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @NotNull(message = "Table cinema_seat: Column row_num cannot be null")
    @Column(name = "row_num")
    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    @NotNull(message = "Table cinema_seat: Column col_num cannot be null")
    @Column(name = "col_num")
    public Integer getColNum() {
        return colNum;
    }

    public void setColNum(Integer colNum) {
        this.colNum = colNum;
    }

    @NotBlank(message = "Table cinema_seat: Column col_num cannot be null nor blank")
    @Size(max = 20, message = "Tabel cinema_seat: Column mark cannot be longer than 50 characters")
    @Column(name = "mark")
    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @JsonIgnore
    @ManyToMany(
            mappedBy = "seats",
            targetEntity = Reservation.class
    )
    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }

}
