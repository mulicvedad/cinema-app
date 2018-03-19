package ba.etf.unsa.nwt.cinemaservice.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "timetable")
public class Timetable extends BaseModel {
    private Date startDateTime;
    private Date endDateTime;
    private Collection<CinemaShowing> cinemaShowings;

    protected Timetable() {}

    public Timetable(Date startDateTime, Date endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Column(name = "start_datetime")
    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Column(name = "end_datetime")
    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    @OneToMany(mappedBy = "timetable")
    public Collection<CinemaShowing> getCinemaShowings() {
        return cinemaShowings;
    }

    public void setCinemaShowings(Collection<CinemaShowing> cinemaShowings) {
        this.cinemaShowings = cinemaShowings;
    }
}
