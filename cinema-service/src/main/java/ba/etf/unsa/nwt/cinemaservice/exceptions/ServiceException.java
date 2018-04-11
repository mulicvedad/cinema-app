package ba.etf.unsa.nwt.cinemaservice.exceptions;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException{

    private String title;
    private HttpStatus status;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(HttpStatus httpStatus) {
        super();
        this.title = "Not found";
        this.status = httpStatus;
    }

    public ServiceException(String title, String message) {
        super(message);
        this.title = title;
    }
}
