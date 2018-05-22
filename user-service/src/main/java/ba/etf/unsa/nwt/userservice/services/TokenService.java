package ba.etf.unsa.nwt.userservice.services;


import ba.etf.unsa.nwt.userservice.controllers.dto.UserAccountDTO;
import ba.etf.unsa.nwt.userservice.exceptions.InvalidTokenException;
import ba.etf.unsa.nwt.userservice.exceptions.TokenNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Component
public class TokenService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Autowired
    private UserService userService;

    private final String HEADER_NAME_AUTHORIZATION = "Authorization";
    private final String PREFIX_AUTHORIZATION = "Bearer ";

    public Authentication getAuthenticationFromRequest(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader(HEADER_NAME_AUTHORIZATION);
        if (token == null)
            throw new TokenNotFoundException("Token is not present in the 'Authorization' header");
        token = token.replace(PREFIX_AUTHORIZATION, "");
        if (!isTokenValid(token))
            throw new InvalidTokenException("Token is either expired or invalid.");
        String username = getUsernameFromToken(token);
        try {
            UserAccountDTO userAccount = userService.findAccountByUsername(username);
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(userAccount.getRole()));
            return new UsernamePasswordAuthenticationToken(userAccount.getUsername(), userAccount.getPasswordHash(),
                    grantedAuthorities);
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody();
            Date now = new Date(System.currentTimeMillis());
            if(claims.getExpiration().before(now))
                return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}