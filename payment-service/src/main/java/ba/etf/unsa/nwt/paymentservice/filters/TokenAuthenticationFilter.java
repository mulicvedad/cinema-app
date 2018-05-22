package ba.etf.unsa.nwt.paymentservice.filters;

import ba.etf.unsa.nwt.paymentservice.exceptions.TokenNotFoundException;
import ba.etf.unsa.nwt.paymentservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {

    @Autowired
    private AuthService authService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            Authentication authentication = authService.getAuthenticationFromRequest((HttpServletRequest) servletRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (TokenNotFoundException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
