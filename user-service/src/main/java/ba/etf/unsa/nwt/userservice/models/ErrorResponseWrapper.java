package ba.etf.unsa.nwt.userservice.models;

public class ErrorResponseWrapper {
    private Error error;

    public ErrorResponseWrapper(Error error) {
        this.error = error;
    }

    public ErrorResponseWrapper() {
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
