package com.hostelbooking.app.config;

import com.hostelbooking.app.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // 🔓 PUBLIC
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/users/register"
                        ).permitAll()

                        // 👮 ADMIN ONLY (PUT THESE FIRST 🔥)
                        .requestMatchers(
                                "/api/bookings/pending",
                                "/api/bookings/*/approve",
                                "/api/bookings/*/reject",
                                "/api/rooms/**"
                        ).hasRole("ADMIN")

                        // 👨‍🎓 STUDENT ONLY
                        .requestMatchers(
                                "/api/bookings",
                                "/api/bookings/user/**",
                                "/api/bookings/*/cancel"
                        ).hasRole("STUDENT")

                        // 👨‍🎓 + 👮 BOTH
                        .requestMatchers(
                                "/api/hostels",
                                "/api/hostels/**"
                        ).hasAnyRole("STUDENT", "ADMIN")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
