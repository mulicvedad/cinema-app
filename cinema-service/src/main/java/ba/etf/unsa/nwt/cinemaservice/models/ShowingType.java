package ba.etf.unsa.nwt.cinemaservice.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull(message = "Table showing_type: Column title cannot be blank nor null")
    @Size(max = 30, message = "Table showing_type: Column title cannot be longer than 30 characters")
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
