package ba.unsa.etf.nwt.movieservice.model;

import java.util.List;
import java.util.Set;

public class TmdbCreditsResponse {
    private int id;
    private List<Crew> crew;

    public TmdbCreditsResponse() {
    }

    public TmdbCreditsResponse(int id,  List<Crew> crew) {
        this.id = id;
        this.crew = crew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
