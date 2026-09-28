package org.ts.orderservice.controller;

import org.springframework.web.bind.annotation.*;
import org.ts.orderservice.model.Order;
import org.ts.orderservice.service.OrderService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /*
     * RestTemplate example
     */
    @PostMapping("/resttemplate")
    public Order restTemplate(
            @RequestParam Long orderId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        return orderService.createWithRestTemplate(
                orderId,
                productId,
                quantity
        );
    }

    /*
     * RestClient example
     */
    @PostMapping("/restclient")
    public Order restClient(
            @RequestParam Long orderId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        return orderService.createWithRestClient(
                orderId,
                productId,
                quantity
        );
    }

    /*
     * WebClient example
     */
    @PostMapping("/webclient")
    public Mono<Order> webClient(
            @RequestParam Long orderId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        return orderService.createWithWebClient(
                orderId,
                productId,
                quantity
        );
    }

    /*
     * OpenFeign example
     */
    @PostMapping("/feign")
    public Order feign(
            @RequestParam Long orderId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        return orderService.createWithFeign(
                orderId,
                productId,
                quantity
        );
    }
}
