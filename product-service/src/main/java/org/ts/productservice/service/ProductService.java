package org.ts.productservice.service;

import org.springframework.stereotype.Service;
import org.ts.productservice.model.Product;

import java.util.List;

@Service
public class ProductService {

    private final List<Product> products = List.of(
            new Product(1L, "iPhone 15", 70000, 10),
            new Product(2L, "Samsung S24", 65000, 15),
            new Product(3L, "MacBook Air", 100000, 5)
    );

    public List<Product> findAll() {
        return products;
    }

    public Product findById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
