package ba.etf.unsa.nwt.cinemaservice.helpers;

import ba.etf.unsa.nwt.cinemaservice.models.Error;
import ba.etf.unsa.nwt.cinemaservice.models.ErrorResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilderHelper {

    public static ResponseEntity badRequest(String type, String field, String description, String fieldValue) {
        if (field != null)
            description += " Field '" + field + "' has value '" + fieldValue;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseWrapper( new Error(type,
                field, description)));
    }

    public static ResponseEntity notFound(String type, String field, String description, String fieldValue) {
        if (field != null)
            description += " Field '" + field + "' has value '" + fieldValue;
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseWrapper( new Error(type,
                field, description)));
    }

}
