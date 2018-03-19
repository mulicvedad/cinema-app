package ba.etf.unsa.nwt.cinemaservice.models;

import javax.persistence.*;
import java.text.CollationElementIterator;
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
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Column(name = "row_num")
    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    @Column(name = "col_num")
    public Integer getColNum() {
        return colNum;
    }

    public void setColNum(Integer colNum) {
        this.colNum = colNum;
    }

    @Column(name = "mark")
    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

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
