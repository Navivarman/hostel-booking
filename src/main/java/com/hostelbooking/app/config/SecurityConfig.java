package com.hostelbooking.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers("/api/users/**").permitAll()

                        // STUDENT access
                        .requestMatchers("/api/bookings").hasRole("STUDENT")
                        .requestMatchers("/api/bookings/**").hasAnyRole("STUDENT","ADMIN")
                        .requestMatchers("/api/hostels/**").hasAnyRole("STUDENT","ADMIN")
                        .requestMatchers("/api/rooms/**").hasAnyRole("STUDENT","ADMIN")

                        // ADMIN access
                        .requestMatchers("/api/bookings/*/approve").hasRole("ADMIN")
                        .requestMatchers("/api/bookings/*/reject").hasRole("ADMIN")
                        .requestMatchers("/api/bookings/*/pending").hasRole("ADMIN")


                        // Everything else
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // TEMP (for testing)

        return http.build();
    }
}

