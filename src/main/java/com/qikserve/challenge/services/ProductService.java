package com.qikserve.challenge.services;

import com.qikserve.challenge.adapters.ProductAdapter;
import com.qikserve.challenge.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Product service.
 */
@Service
public class ProductService {

    @Autowired
    private ProductAdapter productAdapter;

    /**
     * Gets products.
     *
     * @return the products
     */
    public List<Product> getProducts() {
        return productAdapter.getProducts();
    }

    /**
     * Gets product.
     *
     * @param id the id
     * @return the product
     */
    public Product getProduct(String id) {
        return productAdapter.getProduct(id);
    }
}
