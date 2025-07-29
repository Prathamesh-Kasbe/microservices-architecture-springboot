
package dev.pk.ms.filter;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

public class InternalRequestFilter implements WebFilter {

    private final String internalSecret;

    public InternalRequestFilter(String internalSecret) {
        this.internalSecret = internalSecret;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String header = exchange.getRequest().getHeaders().getFirst("X-Internal-Secret");
        System.out.println("InternalREquestFilter________________________________");
        if (!internalSecret.equals(header)) {
        	System.out.println("Internal secret check________________________________");
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }
}