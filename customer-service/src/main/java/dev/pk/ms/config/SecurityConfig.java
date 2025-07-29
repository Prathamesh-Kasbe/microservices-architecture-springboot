package dev.pk.ms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import dev.pk.ms.filter.InternalRequestFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${internal.secret}")
    private String internalSecret;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    	System.out.println("Incoming Header in Customer Service: " + http.headers().toString());
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchange -> exchange
                .anyExchange().permitAll()
            )
            .addFilterAt(new InternalRequestFilter(internalSecret), SecurityWebFiltersOrder.FIRST)
            .build();
    }
}

