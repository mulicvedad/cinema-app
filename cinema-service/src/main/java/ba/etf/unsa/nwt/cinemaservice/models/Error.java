package ba.etf.unsa.nwt.cinemaservice.models;

public class Error {

    private String type;
    private String field;
    private String description;

    public Error(String type, String field, String description) {
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
