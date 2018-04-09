package ba.etf.unsa.nwt.cinemaservice.models;

public class ErrorResponseWrapper {

    private Error error;

    public ErrorResponseWrapper() { }

    public ErrorResponseWrapper(Error error) {
        this.error = error;
    }

    public Error getError() {

        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
