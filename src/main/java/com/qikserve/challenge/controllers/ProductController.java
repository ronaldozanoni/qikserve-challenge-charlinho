package com.qikserve.challenge.controllers;

import com.qikserve.challenge.models.Product;
import com.qikserve.challenge.services.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Product controller.
 */
@Slf4j
@RestController
@Api(value = "Product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Gets products.
     *
     * @return the products
     */
    @ApiOperation(value = "Return a products list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return products"),
            @ApiResponse(code = 500, message = "Exception on get products"),
    })
    @GetMapping(value= {"/products"}, produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    /**
     * Gets product.
     *
     * @param id the id
     * @return the product
     */
    @ApiOperation(value = "Return product by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a product by id"),
            @ApiResponse(code = 500, message = "Exception on get product"),
    })
    @GetMapping(value= {"/products/{id}"}, produces= MediaType.APPLICATION_JSON_VALUE)
    public Product getProduct(@PathVariable String id) {
        log.info("Received query of product by id {}", id);
        return productService.getProduct(id);
    }
}
