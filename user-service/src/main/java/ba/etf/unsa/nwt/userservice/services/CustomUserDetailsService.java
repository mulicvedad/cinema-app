package ba.etf.unsa.nwt.userservice.services;

import ba.etf.unsa.nwt.userservice.controllers.dto.UserAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserAccountDTO userAccount = userService.findAccountByUsername(s);
        if (userAccount == null)
            throw new UsernameNotFoundException("User with username '" + s + "' not found");
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(userAccount.getRole() != null) {
            grantedAuthorities.add(new SimpleGrantedAuthority(userAccount.getRole()));
        }
        return new User(userAccount.getUsername(), userAccount.getPasswordHash(), grantedAuthorities);
    }

}