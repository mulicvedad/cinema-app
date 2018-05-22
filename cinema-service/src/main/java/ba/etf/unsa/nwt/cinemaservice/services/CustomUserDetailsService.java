package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.controllers.dto.UserAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserAccountDTO userAccount = authService.findUserByUsername(s);
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(userAccount.getRole() != null) {
            grantedAuthorities.add(new SimpleGrantedAuthority(userAccount.getRole()));
        }
        return new User(userAccount.getUsername(), userAccount.getPasswordHash(), grantedAuthorities);
    }

}
