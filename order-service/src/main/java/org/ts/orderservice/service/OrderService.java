package org.ts.orderservice.service;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.ts.orderservice.client.ProductFeignClient;
import org.ts.orderservice.model.Order;
import org.ts.orderservice.model.Product;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private static final String PRODUCT_URL =
            "http://product-service/api/products/{id}";

    private final RestTemplate restTemplate;
    private final RestClient restClient;
    private final WebClient webClient;
    private final ProductFeignClient feignClient;

    public OrderService(
            RestTemplate restTemplate,
            RestClient restClient,
            WebClient.Builder webClientBuilder,
            ProductFeignClient feignClient
    ) {
        this.restTemplate = restTemplate;
        this.restClient = restClient;
        this.webClient = webClientBuilder.build();
        this.feignClient = feignClient;
    }

    /*
     * 1. RestTemplate + Eureka + LoadBalancer
     */
    public Order createWithRestTemplate(
            Long orderId,
            Long productId,
            int quantity
    ) {
        Product product = restTemplate.getForObject(
                PRODUCT_URL,
                Product.class,
                productId
        );

        return createOrder(
                orderId,
                productId,
                quantity,
                product,
                "RestTemplate"
        );
    }

    /*
     * 2. RestClient + Eureka + LoadBalancer
     */
    public Order createWithRestClient(
            Long orderId,
            Long productId,
            int quantity
    ) {
        Product product = restClient
                .get()
                .uri(PRODUCT_URL, productId)
                .retrieve()
                .body(Product.class);

        return createOrder(
                orderId,
                productId,
                quantity,
                product,
                "RestClient"
        );
    }

    /*
     * 3. WebClient + Eureka + LoadBalancer
     */
    public Mono<Order> createWithWebClient(
            Long orderId,
            Long productId,
            int quantity
    ) {
        return webClient
                .get()
                .uri(PRODUCT_URL, productId)
                .retrieve()
                .bodyToMono(Product.class)
                .map(product ->
                        createOrder(
                                orderId,
                                productId,
                                quantity,
                                product,
                                "WebClient"
                        )
                );
    }

    /*
     * 4. OpenFeign + Eureka + LoadBalancer
     */
    public Order createWithFeign(
            Long orderId,
            Long productId,
            int quantity
    ) {
        Product product = feignClient.getProduct(productId);

        return createOrder(
                orderId,
                productId,
                quantity,
                product,
                "FeignClient"
        );
    }

    private Order createOrder(
            Long orderId,
            Long productId,
            int quantity,
            Product product,
            String clientUsed
    ) {
        if (product == null) {
            throw new RuntimeException(
                    "Product not found: " + productId
            );
        }

        if (product.getStock() < quantity) {
            throw new RuntimeException(
                    "Not enough stock for product: " + productId
            );
        }

        double total = product.getPrice() * quantity;

        return new Order(
                orderId,
                productId,
                quantity,
                total,
                product,
                clientUsed
        );
    }
}
