package ba.unsa.etf.nwt.authservice.services;

import ba.unsa.etf.nwt.authservice.models.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class TokenService {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-duration}")
    private int TOKEN_DURATION_IN_MINUTES;

    @Value("${jwt.token-issuer}")
    private String TOKEN_ISSUER;

    private final int ONE_MINUTE_IN_MILLIS = 60000;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public String generateToken(UserAccount user) {

        final long nowMillis = System.currentTimeMillis();
        final long expMillis = nowMillis + (ONE_MINUTE_IN_MILLIS * TOKEN_DURATION_IN_MINUTES);

        final Map<String, Object> roles = new HashMap<>();
        roles.put("admin", user.isAdmin());

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, this.signatureAlgorithm.getJcaName());

        return Jwts
                .builder()
                .setClaims(roles)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(expMillis))
                .setSubject(user.getUsername())
                .setIssuer(this.TOKEN_ISSUER)
                .signWith(this.signatureAlgorithm, signingKey)
                .compact();
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            claims.put("created", new Date(System.currentTimeMillis()));
            refreshedToken = this.generateTokenFromClaims(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    private String generateTokenFromClaims(Map<String, Object> claims) {
        final long expMillis =  System.currentTimeMillis() + (ONE_MINUTE_IN_MILLIS * TOKEN_DURATION_IN_MINUTES);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(expMillis))
                .signWith(this.signatureAlgorithm, this.secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            created = new Date((Long) claims.get("created"));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(this.secretKey)
                    .parseClaimsJws(token).getBody();
            Date now = new Date(System.currentTimeMillis());
            if(claims.getExpiration().before(now))
                return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        return true;
    }

    public Boolean validateToken(String token, UserAccount userDetails) {
        return true;
    }

}
