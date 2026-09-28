package org.ts.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("product-service", route -> route
                        .path("/api/products/**")
                        .uri("lb://PRODUCT-SERVICE"))

                .route("order-service", route -> route
                        .path("/api/orders/**")
                        .uri("lb://ORDER-SERVICE"))

                .build();
    }
}
