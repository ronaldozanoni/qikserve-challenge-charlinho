package com.qikserve.challenge.adapters;

import com.qikserve.challenge.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * The type Product adapter.
 */
@Component
public class ProductAdapter {

    @Value("${app.wiremockUrl}")
    private String wiremockUrl;

    private WebClient webClient;

    /**
     * Gets products.
     *
     * @return the products
     */
    public List<Product> getProducts() {
        WebClient webClient = WebClient.create(wiremockUrl);
        return webClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(Product.class)
                .collectList()
                .block();
    }

    /**
     * Gets product.
     *
     * @param id the id
     * @return the product
     */
    public Product getProduct(String id) {
        WebClient webClient = WebClient.create(wiremockUrl);
        return webClient.get()
                .uri("/products/" + id)
                .retrieve()
                .bodyToFlux(Product.class)
                .blockFirst();
    }
}
