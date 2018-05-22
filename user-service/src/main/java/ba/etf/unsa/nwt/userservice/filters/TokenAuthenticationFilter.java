package ba.etf.unsa.nwt.userservice.filters;


import ba.etf.unsa.nwt.userservice.exceptions.InvalidTokenException;
import ba.etf.unsa.nwt.userservice.exceptions.TokenNotFoundException;
import ba.etf.unsa.nwt.userservice.services.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {

    @Autowired
    private TokenService tokenService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            Authentication authentication = tokenService.getAuthenticationFromRequest((HttpServletRequest) servletRequest);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ((HttpServletResponse)servletResponse).setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        } catch (TokenNotFoundException e) {
            //setForbiddenStatus(servletResponse, e.getMessage());
        } catch (InvalidTokenException e) {
            //setForbiddenStatus(servletResponse, e.getMessage());
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

    private void setForbiddenStatus(ServletResponse response, String message) throws IOException{
        ((HttpServletResponse)response).setStatus(HttpStatus.FORBIDDEN.value());
        // Temporary solution (should be replaced by ApiError)
        Map<String, String> errorWrapper = new HashMap<>();
        errorWrapper.put("error", message);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorWrapper));
    }

}