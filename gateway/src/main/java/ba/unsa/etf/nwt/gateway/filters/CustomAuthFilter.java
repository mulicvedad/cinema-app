package ba.unsa.etf.nwt.gateway.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CustomAuthFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(SimpleFilter.class);

    @Value("${jwt.secret-key}")
    private String jwtKey;

    public CustomAuthFilter(String apiKey) {
        this.jwtKey = apiKey;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        // Error hanling in this method is not good
        // Response should contain object that has all the useful information for the caller in the form of JSON object
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        String token = request.getHeader("Authorization");
        log.info(token);
        if(token != null) {
            token = token.replace("Bearer ", "");
            log.info(token);
            if(isTokenValid(token)) {
                filterChain.doFilter(request,response);
            }else {
                response.setStatus(403);
                response.getWriter().write("Token is Either Expired or Modified");
            }
        }else {
            response.setStatus(403);
            response.getWriter().write("You need the access token to access this resource");
        }
    }

    private boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtKey)
                    .parseClaimsJws(token).getBody();
            Date now = new Date(System.currentTimeMillis());

            if(claims.getExpiration().before(now))
                return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void destroy() { }


}
