package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.controllers.dto.UserAccountDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class TokenService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Autowired
    private UserService userService;

    public Authentication getAuthenticationFromRequest(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        if (token != null) {
            token = token.replace("Bearer ", "");
            String username = getUsernameFromToken(token);
            try {
                UserAccountDTO userAccount = userService.findByUsername(username);
                Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                grantedAuthorities.add(new SimpleGrantedAuthority(userAccount.getRole()));
                if (BCrypt.checkpw( "password", userAccount.getPasswordHash()))
                    LoggerFactory.getLogger(this.getClass()).info("SUCCESS");
                else
                    LoggerFactory.getLogger(this.getClass()).info("FAIL");
                return new UsernamePasswordAuthenticationToken(userAccount.getUsername(), userAccount.getPasswordHash(),
                        grantedAuthorities);
            } catch (Exception e) {
                return null;
            }
        }
        return null;

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

}
