package ba.unsa.etf.nwt.authservice.models;

public class ErrorResponseWrapper {
    private ApiError error;

    public ErrorResponseWrapper(ApiError error) {
        this.error = error;
    }

    public ErrorResponseWrapper() {
    }

    public ApiError getError() {
        return error;
    }

    public void setError(ApiError error) {
        this.error = error;
    }
}
