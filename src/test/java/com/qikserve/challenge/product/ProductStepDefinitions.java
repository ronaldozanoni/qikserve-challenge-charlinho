package com.qikserve.challenge.product;

import com.qikserve.challenge.models.Product;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductStepDefinitions {

    private final String SERVER_URL = "http://localhost";

    @LocalServerPort
    private int port;

    private Product product;
    private Product[] products;

    private final RestTemplate restTemplate = new RestTemplate();

    @When("^user wanna a products list")
    public void getProducts() {
        products = restTemplate.getForEntity(endPoint() + "/products", Product[].class).getBody();
    }

    @Then("^should return a product list")
    public void shouldReturnProductList() {
        assertThat(products.length).isGreaterThan(1);
    }

    @When("^user wanna one product by id (\\w+)")
    public void getProduct(String id) {
        product = restTemplate.getForEntity(endPoint() + "/products/" + id, Product.class).getBody();
    }

    @Then("^should return one product by id")
    public void shouldReturnOneProductById() {
        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo("PWWe3w1SDU");
    }

    private String endPoint() {
        return SERVER_URL + ":" + port;
    }
}
