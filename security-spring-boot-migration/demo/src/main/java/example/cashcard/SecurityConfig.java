package example.cashcard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/cashcards/**").hasRole("CARD-OWNER")
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/cashcards/demo");
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails sarah = User.builder()
                .username("sarah1")
                .password(passwordEncoder().encode("abc123"))
                .roles("CARD-OWNER")
                .build();
        UserDetails kumar =User.builder()
                .username("kumar2")
                .password(passwordEncoder().encode("xyz789"))
                .roles("CARD-OWNER")
                .build();
        UserDetails outsider = User.builder()
                .username("non-owner3")
                .password(passwordEncoder().encode("non-owner3"))
                .roles("SOME-OTHER-ROLE")
                .build();

        return new InMemoryUserDetailsManager(sarah,kumar,outsider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
