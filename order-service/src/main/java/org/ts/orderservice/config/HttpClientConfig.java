package org.ts.orderservice.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HttpClientConfig {

    /*
     * RestTemplate:
     * http://product-service/api/products/1
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*
     * RestClient:
     * http://product-service/api/products/1
     */
    // RestClient built manually, not a RestClient.Builder bean
    @Bean
    public RestClient productRestClient(LoadBalancerClient loadBalancerClient) {
        return RestClient.builder()
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
                .build();
    }

    /*
     * WebClient:
     * http://product-service/api/products/1
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
