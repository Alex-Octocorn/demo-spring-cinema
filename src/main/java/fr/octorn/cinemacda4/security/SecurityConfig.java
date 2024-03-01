package fr.octorn.cinemacda4.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//
//        BCryptPasswordEncoder encoder = passwordEncoder();
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password(encoder.encode("user"))
//                .roles("USER")
//                .build();
//
//        UserDetails employee = User.builder()
//                .username("employee")
//                .password(encoder.encode("employee"))
//                .roles("USER", "EMPLOYEE")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(encoder.encode("admin"))
//                .roles("USER", "EMPLOYEE", "ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, employee, admin);
//    }

    @Bean
    public SecurityFilterChain defaultSecurityChain(
            HttpSecurity http
    ) throws Exception {
            http.authorizeHttpRequests(
                    authorizedRequests -> authorizedRequests

                            .requestMatchers(HttpMethod.GET, "/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/register").permitAll()
                            .requestMatchers(HttpMethod.POST, "/seances/{id}/reserver").hasRole("USER")
                            .requestMatchers(HttpMethod.POST, "/**").hasRole("EMPLOYEE")
                            .requestMatchers(HttpMethod.PUT, "/**").hasRole("EMPLOYEE")
                            .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                            .anyRequest().authenticated()

            ).formLogin(Customizer.withDefaults());

            http.httpBasic(Customizer.withDefaults());

            http.csrf(AbstractHttpConfigurer::disable);

            return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
