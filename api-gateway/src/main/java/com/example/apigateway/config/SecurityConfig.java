package com.example.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity // Required for Spring Cloud Gateway (WebFlux based)
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .authorizeExchange(exchanges -> exchanges
                // Example: Allow actuator health endpoint without authentication
                .pathMatchers("/actuator/health/**", "/actuator/prometheus/**","/actuator/info/**").permitAll()
                // All other exchanges require authentication
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults()) // Configure JWT validation with defaults based on issuer-uri
            );
            // TODO: Add CSRF protection if dealing with stateful browser interactions through gateway,
            // for stateless APIs, it might be disabled or handled differently.
            // http.csrf(csrf -> csrf.disable()); // Example for stateless APIs

        return http.build();
    }
}
