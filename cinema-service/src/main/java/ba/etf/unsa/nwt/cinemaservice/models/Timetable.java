package ba.etf.unsa.nwt.cinemaservice.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "timetable")
public class Timetable extends BaseModel {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date startDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date endDateTime;
    private Collection<CinemaShowing> cinemaShowings;

    protected Timetable() {}

    public Timetable(Date startDateTime, Date endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @NotNull(message = "Table timetable: Column start_datetime cannot be null")
    @Column(name = "start_datetime")
    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }


    @NotNull(message = "Table timetable: Column end_datetime cannot be null")
    @Column(name = "end_datetime")
    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "timetable")
    public Collection<CinemaShowing> getCinemaShowings() {
        return cinemaShowings;
    }

    public void setCinemaShowings(Collection<CinemaShowing> cinemaShowings) {
        this.cinemaShowings = cinemaShowings;
    }

}
