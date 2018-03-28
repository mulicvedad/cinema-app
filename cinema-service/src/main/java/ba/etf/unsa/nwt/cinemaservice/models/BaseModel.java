package ba.etf.unsa.nwt.cinemaservice.models;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseModel {
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
