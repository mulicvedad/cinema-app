package ba.etf.unsa.nwt.cinemaservice.models;

public class ErrorResponse {

    private String field;
    private String description;

    public ErrorResponse(String field, String description) {
        this.field = field;
        this.description = description;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
