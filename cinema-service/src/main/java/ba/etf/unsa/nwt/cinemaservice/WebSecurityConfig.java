package ba.etf.unsa.nwt.cinemaservice;

import ba.etf.unsa.nwt.cinemaservice.filters.TokenAuthenticationFilter;
import ba.etf.unsa.nwt.cinemaservice.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final String ROLE_NAME_ADMIN = "ADMIN";

    @Autowired
    TokenAuthenticationFilter tokenAuthenticationFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/reservations/**").authenticated()
                .antMatchers(HttpMethod.GET, "/reservation-statuses/**").authenticated()
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .antMatchers("/reservation-statuses/**").hasRole(ROLE_NAME_ADMIN)
                .antMatchers("/cinema-showings/**").hasRole(ROLE_NAME_ADMIN)
                .antMatchers("/rooms/**").hasRole(ROLE_NAME_ADMIN)
                .antMatchers("/showing-types/**").hasRole(ROLE_NAME_ADMIN)
                .antMatchers("/news/**").hasRole(ROLE_NAME_ADMIN)
                .antMatchers("/cinema-seats/**").hasRole(ROLE_NAME_ADMIN)
                .antMatchers("*").denyAll();

        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

}
