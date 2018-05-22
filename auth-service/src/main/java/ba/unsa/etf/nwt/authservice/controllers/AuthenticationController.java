package ba.unsa.etf.nwt.authservice.controllers;

import ba.unsa.etf.nwt.authservice.controllers.dto.LoginRequest;
import ba.unsa.etf.nwt.authservice.controllers.dto.UserAccountDTO;
import ba.unsa.etf.nwt.authservice.models.UserAccount;
import ba.unsa.etf.nwt.authservice.services.AuthenticationService;
import ba.unsa.etf.nwt.authservice.services.TokenService;
import ba.unsa.etf.nwt.authservice.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        // In this method it is important to return the reason of failure along with the 400 response
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        if (username == null || password == null || username.isEmpty() || password.isEmpty())
            return ResponseEntity.badRequest().build();
        String token = this.authenticationService.generateToken(username, password);
        if (token != null){
            return ResponseEntity.ok().body(authenticationService.generateResponseSucc(username, token));
        }
        return ResponseEntity.badRequest().build();
    }

    // username can be omitted (null)
    @GetMapping("/authenticate")
    public ResponseEntity authenticate( @RequestParam String token, @RequestParam(required = false) String username) {
        UserAccountDTO userAccount = authenticationService.authenticate(token, username);
        if (userAccount != null)
            return ResponseEntity.ok(userAccount);
        else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
