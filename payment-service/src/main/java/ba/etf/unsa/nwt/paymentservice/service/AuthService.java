package ba.etf.unsa.nwt.paymentservice.service;

import ba.etf.unsa.nwt.paymentservice.domain.UserAccountDTO;
import ba.etf.unsa.nwt.paymentservice.exceptions.TokenNotFoundException;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

@Service
public class AuthService {

    @Autowired
    @Qualifier("eurekaClient")
    private EurekaClient eurekaClient;

    @Autowired
    private RestTemplate restTemplate;

    private String currentJwt;

    public Authentication getAuthenticationFromRequest(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        if (token == null)
            throw new TokenNotFoundException("Token is not present in the 'Authorization' header");
        token = token.replace("Bearer ", "");
        currentJwt = token;
        try {
            UserAccountDTO userAccount = authenticate(token);
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(userAccount.getRole()));
            return new UsernamePasswordAuthenticationToken(userAccount.getUsername(), userAccount.getPasswordHash(),
                    grantedAuthorities);
        } catch (Exception e) {
            return null;
        }
    }

    public UserAccountDTO authenticate(String token) {
        String url;
        try {
            InstanceInfo instanceInfo = eurekaClient.getApplication("auth-service").getInstances().get(0);
            url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/"
                    + "authenticate?token=" + token;
            Logger.getLogger(this.getClass().toString()).info("URL " + url);
        } catch (Exception e) {
            throw new RuntimeException("Communication failure. Cannot establish communication with service 'auth-service'.");
        }
        try {
            ResponseEntity<UserAccountDTO> response = restTemplate.getForEntity(url, UserAccountDTO.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return null;
            }
        } catch (HttpClientErrorException e) {
            return null;
        }
    }

    public UserAccountDTO findUserByUsername(String username) {
        String url;
        try {
            InstanceInfo instanceInfo = eurekaClient.getApplication("auth-service").getInstances().get(0);
            url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/"
                    + "authenticate?token=" + currentJwt + "&username=" + username;
            Logger.getLogger(this.getClass().toString()).info("URL " + url);
        } catch (Exception e) {
            throw new RuntimeException("Communication failure. Cannot establish communication with service 'auth-service'.");
        }
        try {
            ResponseEntity<UserAccountDTO> response = restTemplate.getForEntity(url, UserAccountDTO.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                 throw new UsernameNotFoundException("User with username '" + username + "' not found.");
            }
        } catch (HttpClientErrorException e) {
            throw new UsernameNotFoundException("User with username '" + username + "' not found.");
        }
    }

}
