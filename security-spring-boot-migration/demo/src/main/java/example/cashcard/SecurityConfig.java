package example.cashcard;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                        .antMatchers("/cashcards/**").hasRole("CARD-OWNER")
                .and()
                    .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetails sarah = User.withDefaultPasswordEncoder()
                .username("sarah1")
                .password("abc123")
                .roles("CARD-OWNER")
                .build();
        UserDetails kumar =User.withDefaultPasswordEncoder()
                .username("kumar2")
                .password("xyz789")
                .roles("CARD-OWNER")
                .build();
        UserDetails outsider = User.withDefaultPasswordEncoder()
                .username("non-owner3")
                .password("non-owner3")
                .roles("SOME-OTHER-ROLE")
                .build();
        auth.inMemoryAuthentication().withUser(sarah).withUser(kumar).withUser(outsider);
    }
}
