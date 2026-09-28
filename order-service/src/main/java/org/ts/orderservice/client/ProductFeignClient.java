package org.ts.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.ts.orderservice.model.Product;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

    @GetMapping("/api/products/{id}")
    Product getProduct(@PathVariable("id") Long id);
}
