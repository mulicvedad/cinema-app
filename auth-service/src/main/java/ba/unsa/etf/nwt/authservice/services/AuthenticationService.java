package ba.unsa.etf.nwt.authservice.services;

import ba.unsa.etf.nwt.authservice.controllers.dto.UserAccountDTO;
import ba.unsa.etf.nwt.authservice.models.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserAccountService userAccountService;

    public String generateToken(String username, String password) {
        Optional<UserAccount> userAccount = userAccountService.findByUsername(username);
        if (userAccount.isPresent()) {
            if (BCrypt.checkpw(password, userAccount.get().getPasswordHash()))
                return tokenService.generateToken(userAccount.get());
        }
        return null;
    }

    public Map<String, Object> generateResponseSucc(String username, String token) {
        Map<String, Object> response = new HashMap<>();
        response.put("jwt", token);
        UserAccount user = this.userAccountService.findByUsername(username).get();
        response.put("user", user.getUserDto());
        response.put("tokenExpirationDate", this.tokenService.getExpirationDateFromToken(token));
        // Roles are already contained in the user object but in that implementation user can have at most one role
        // response.put("roles", this.userAccountService.getRoles(username));
        return response;
    }

    public UserAccountDTO authenticate(String token, String username) {
        try {
            if (!tokenService.isTokenValid(token))
                return null;
            String tokenUsername = tokenService.getUsernameFromToken(token);
            if (username != null)
                if(!tokenUsername.equals(username))
                    return null;
            Optional<UserAccount> userAccount = userAccountService.findByUsername(tokenUsername);
            if (userAccount.isPresent())
                return new UserAccountDTO(userAccount.get().getUsername(), userAccount.get().getPasswordHash(),
                        userAccount.get().getRole().getName());
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
