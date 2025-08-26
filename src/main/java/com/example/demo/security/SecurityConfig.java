package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf(AbstractHttpConfigurer::disable)                    // Disable CSRF for APIs
                    .headers(headers -> headers
                            .frameOptions().sameOrigin()                 // Allow H2 console frames
                    )
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/h2-console/**").permitAll()   // Allow H2 console
                            .requestMatchers("/api/**").permitAll()         // Allow your API endpoints
                            .requestMatchers("/products/**").permitAll()    // Allow product endpoints
                            .anyRequest().permitAll()                       // Allow everything else for now
                    );

            return http.build();
        }
}
