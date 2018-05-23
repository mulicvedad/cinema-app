package ba.etf.unsa.nwt.userservice;


import ba.etf.unsa.nwt.userservice.filters.TokenAuthenticationFilter;
import ba.etf.unsa.nwt.userservice.services.CustomUserDetailsService;
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
                .antMatchers(HttpMethod.GET, "/users").permitAll()
                .antMatchers("/users/find**").authenticated()
                .antMatchers(HttpMethod.GET, "/users/*/details").authenticated()
                .antMatchers(HttpMethod.POST, "/users/register").permitAll()
                .antMatchers(HttpMethod.DELETE, "/users/*").hasRole(ROLE_NAME_ADMIN)
                .antMatchers(HttpMethod.PUT, "/users/*").authenticated()
                .antMatchers(HttpMethod.PUT, "/users/*/reset-password").authenticated()
                .anyRequest().denyAll();        // black list approach

        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}

