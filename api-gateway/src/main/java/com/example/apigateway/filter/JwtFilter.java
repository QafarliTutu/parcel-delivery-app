package com.example.apigateway.filter;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtFilter implements GatewayFilter {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var authHeader = exchange
                .getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
        var response = exchange.getResponse();

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.error("Jwt either null or doesn't Bearer");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        var token = authHeader.substring(7);

        try {
            var claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();

            var userId = claims.getSubject();
            var roles = (List<String>) claims.get("roles");

            exchange.getRequest()
                    .mutate()
                    .headers(httpHeaders -> {
                        httpHeaders.add("userID", userId);
                        httpHeaders.add("roles", String.join(",", roles));})
                    .build();

            return chain.filter(exchange);

        }
        catch (JwtException jwtException) {
            log.error(jwtException.getMessage());
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        catch (Exception e) {
            log.error(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_GATEWAY);
            return response.setComplete();
        }
    }
}
