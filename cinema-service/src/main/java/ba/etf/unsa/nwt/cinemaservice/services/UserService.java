package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.controllers.CinemaShowingController;
import ba.etf.unsa.nwt.cinemaservice.controllers.dto.UserAccountDTO;
import ba.etf.unsa.nwt.cinemaservice.exceptions.ServiceException;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("eurekaClient")
    private EurekaClient eurekaClient;

    public UserAccountDTO findByUsername(String username) {
        String url;
        try {
            InstanceInfo instanceInfo = eurekaClient.getApplication("user-service").getInstances().get(0);
            url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "/users/find?username=" + username;
            Logger.getLogger(CinemaShowingController.class.toString()).info("URL " + url);
        } catch (Exception e) {
            throw new ServiceException("Communication failure. Cannot establish communication with service 'user-service'.");
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
