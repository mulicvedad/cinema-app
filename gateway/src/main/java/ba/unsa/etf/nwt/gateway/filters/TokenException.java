package ba.unsa.etf.nwt.gateway.filters;

import javax.servlet.ServletException;

public class TokenException extends ServletException {

    public TokenException(String message) {
        super(message);
    }

}
