package com.example.apigateway.config;

import com.example.apigateway.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouteConfig {

    private final JwtFilter jwtFilter;

    @Value("${app.services.user}")
    private String userURI;

    @Value("${app.services.order}")
    private String orderURI;

    @Value("${app.services.delivery}")
    private String deliveryURI;


    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user", r -> r
                        .path("/api/v1/users/**")
                        .uri(userURI + ":8081"))
                .route("order", r -> r
                        .path("/api/v1/orders/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri(orderURI + ":8082"))
                .route("delivery", r -> r
                        .path("/api/v1/deliveries/**")
                        .or()
                        .path("/api/v1/couriers/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri(deliveryURI + ":8083"))
                .build();
    }

}
