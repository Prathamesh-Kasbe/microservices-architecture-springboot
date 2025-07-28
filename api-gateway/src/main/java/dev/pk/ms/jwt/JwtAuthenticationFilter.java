package dev.pk.ms.jwt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtProperties jwtProperties;

    public JwtAuthenticationFilter() {
        super(Config.class);
        System.out.println("JwtAuthenticationFilter constructor called");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return this::handleRequest; // method reference instead of lambda
    }

    private Mono<Void> handleRequest(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Before Bearer check________________________________");

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("In Bearer check________________________________");
            return onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        try {
            Claims claims = jwtUtil.extractClaims(token);
            List<String> roles = claims.get("roles", List.class);
            String path = exchange.getRequest().getPath().toString();

            if (path.contains("/customer") && !roles.contains("CUSTOMER")) {
                System.out.println("In customer Before updating exchange in api gateway________________________________");
                return onError(exchange, "Forbidden", HttpStatus.FORBIDDEN);
            } else if (path.contains("/account") && !roles.contains("ACCOUNT")) {
                System.out.println("In account Before updating exchange in api gateway________________________________");
                return onError(exchange, "Forbidden", HttpStatus.FORBIDDEN);
            }

        } catch (Exception e) {
            System.out.println("In Catch block Before updating exchange in api gateway________________________________");
            e.printStackTrace();
            return onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
        }

        // Add Internal secret to header
        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .header("X-Internal-Secret", jwtProperties.getSecret())
                .build();

        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
        System.out.println("After updating exchange in api gateway________________________________");

        return chain.filter(mutatedExchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
        System.out.println("Invalid token detected, returning " + status);
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
        // Define your config fields if needed
    }
}

