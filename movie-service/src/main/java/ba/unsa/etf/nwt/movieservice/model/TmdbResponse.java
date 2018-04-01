package ba.unsa.etf.nwt.movieservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TmdbResponse {
    private int page;
    private int totalResults;
    private int totalPages;
    private List<Movie> results;

    public TmdbResponse() {
    }

    @JsonCreator
    public TmdbResponse(@JsonProperty("page") int page,
                        @JsonProperty("results") List<Movie> results,
                        @JsonProperty("total_results") int totalResults,
                        @JsonProperty("total_pages") int totalPages) {
        this.page = page;
        this.results = results;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}

