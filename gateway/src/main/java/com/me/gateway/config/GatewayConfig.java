package com.me.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

  @Bean
  RouteLocator gateway(RouteLocatorBuilder rlb) {
    return rlb.routes()
        .route(
            rs ->
                rs.path("/")
                    .filters(GatewayFilterSpec::tokenRelay)
                    .uri("http://localhost:8080")) // resource server
        .route(
            rs ->
                rs.path("/gw")
                    .filters(
                        gatewayFilterSpec -> {
                          gatewayFilterSpec.tokenRelay();
                          return gatewayFilterSpec.setPath("/me");
                        })
                    .uri("http://localhost:8080")) // res
        .build();
  }
}
