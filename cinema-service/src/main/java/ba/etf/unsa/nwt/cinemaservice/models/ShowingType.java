package ba.etf.unsa.nwt.cinemaservice.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "showing_type")
public class ShowingType extends BaseModel{
    private String title;
    private Collection<CinemaShowing> cinemaShowings;

    protected ShowingType() {}

    public ShowingType(String title) {
        this.title = title;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "showingType")
    public Collection<CinemaShowing> getCinemaShowings() {
        return cinemaShowings;
    }

    public void setCinemaShowings(Collection<CinemaShowing> cinemaShowings) {
        this.cinemaShowings = cinemaShowings;
    }
}
