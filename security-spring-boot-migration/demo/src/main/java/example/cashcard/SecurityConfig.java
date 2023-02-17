package example.cashcard;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;

    public SecurityConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                        .antMatchers("/cashcards/**").hasRole("CARD-OWNER")
                .and()
                    .httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/cashcards/demo");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        UserDetails kumar = User.withDefaultPasswordEncoder()
                .username("kumar2")
                .password("xyz789")
                .roles("CARD-OWNER")
                .build();
        UserDetails outsider = User.withDefaultPasswordEncoder()
                .username("non-owner3")
                .password("non-owner3")
                .roles("SOME-OTHER-ROLE")
                .build();

        auth.jdbcAuthentication()
                .withDefaultSchema()
                .dataSource(dataSource)
                .withUser(kumar).withUser(outsider);
        auth.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String username = authentication.getName();
                String password = authentication.getCredentials().toString();
                if ("sarah1".equals(username) && "abc123".equals(password)) {
                    return new UsernamePasswordAuthenticationToken
                            (username, password, new ArrayList() {{
                                add(new SimpleGrantedAuthority("ROLE_CARD-OWNER"));
                            }});
                } else {
                    throw new
                            BadCredentialsException("External system authentication failed");
                }
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.equals(UsernamePasswordAuthenticationToken.class);
            }
        });
    }
}
