package ba.etf.unsa.nwt.userservice.models;

public class Error {
    private String field;
    private String message;
    private Integer status;

    public Error(String field, String message, Integer status) {
        this.field = field;
        this.message = message;
        this.status = status;
    }

    public Error() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
