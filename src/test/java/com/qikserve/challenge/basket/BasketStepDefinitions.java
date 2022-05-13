package com.qikserve.challenge.basket;

import com.qikserve.challenge.dtos.BasketRequest;
import com.qikserve.challenge.dtos.BasketResponse;
import com.qikserve.challenge.dtos.CheckoutRequest;
import com.qikserve.challenge.dtos.CheckoutResponse;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BasketStepDefinitions {

    private final String SERVER_URL = "http://localhost";

    @LocalServerPort
    private int port;

    private BasketResponse basketResponse;

    private CheckoutResponse checkoutResponse;

    private final RestTemplate restTemplate = new RestTemplate();

    @When("user {int} add {int} item in basket")
    public void addItemInBasket(int userId, int amount) {
        basketResponse = restTemplate.postForObject(
                endPoint() + "/basket",
                BasketRequest.builder()
                            .amount(amount)
                            .userId(userId)
                            .productId("PWWe3w1SDU")
                        .build(),
                BasketResponse.class
        );
    }

    @Then("should return item in contents list")
    public void shouldReturnContents() {
        assertThat(basketResponse.getContents()).isNotNull().isNotEmpty();
    }

    @When("^user add (\\w+) in basket")
    public void addItemBurgerInBasket(String item) {
        basketResponse = restTemplate.postForObject(
                endPoint() + "/basket",
                BasketRequest.builder()
                        .amount(1)
                        .userId(1)
                        .productId(item)
                        .build(),
                BasketResponse.class
        );
    }

    @Then("should return added item")
    public void shouldReturnAddedItem() {
        final String productName = "Amazing Burger!";

        BasketResponse.Content content =
                basketResponse.getContents().stream()
                            .filter((c) -> c.getProductName().equals(productName)).findFirst().orElse(null);

        assertThat(content).isNotNull();
        assertThat(content.getProductName()).isEqualTo(productName);
    }

    @When("^user add items in basket with promotion code (\\w+)")
    public void addBurgerInBasketWithPromotionCode(String promotionCode) {
        basketResponse = restTemplate.postForObject(
                endPoint() + "/basket",
                BasketRequest.builder()
                        .amount(2)
                        .userId(1)
                        .productId("PWWe3w1SDU")
                        .promotionCode(promotionCode)
                        .build(),
                BasketResponse.class
        );
    }

    @Then("should return two burgers with promotion")
    public void shouldReturnContentWithPromotion() {
        assertThat(basketResponse.getRawValue()).isEqualTo(BigDecimal.valueOf(60));
        assertThat(basketResponse.getTotalPromos()).isEqualTo(BigDecimal.valueOf(15));
        assertThat(basketResponse.getTotalPayable()).isEqualTo(BigDecimal.valueOf(45));
    }

    @When("user do checkout")
    public void userDoCheckout() {
        checkoutResponse = restTemplate.postForObject(
                endPoint() + "/basket/checkout",
                CheckoutRequest.builder()
                        .userId(1)
                    .build(),
                CheckoutResponse.class
        );
    }

    @Then("should return total paid")
    public void shouldReturnTotalPaid() {
        assertThat(checkoutResponse.getTotalPaid()).isEqualTo(BigDecimal.valueOf(45));
    }

    @When("^user add pizza in basket with promotion code (\\w+)")
    public void addPizzaInBasketWithPromotionCode(String promotionCode) {
        basketResponse = restTemplate.postForObject(
                endPoint() + "/basket",
                BasketRequest.builder()
                        .amount(2)
                        .userId(1)
                        .productId("Dwt5F7KAhi")
                        .promotionCode(promotionCode)
                        .build(),
                BasketResponse.class
        );
    }

    @Then("should return pizza with promotion")
    public void shouldReturnPizzaWithPromotion() {
        assertThat(basketResponse.getRawValue()).isEqualTo(BigDecimal.valueOf(150));
        assertThat(basketResponse.getTotalPromos()).isEqualTo(BigDecimal.valueOf(75));
        assertThat(basketResponse.getTotalPayable()).isEqualTo(BigDecimal.valueOf(75));
    }

    @When("^user add salad in basket with promotion code (\\w+)")
    public void addSaladInBasketWithPromotionCode(String promotionCode) {
        basketResponse = restTemplate.postForObject(
                endPoint() + "/basket",
                BasketRequest.builder()
                        .amount(1)
                        .userId(1)
                        .productId("C8GDyLrHJb")
                        .promotionCode(promotionCode)
                        .build(),
                BasketResponse.class
        );
    }

    @Then("should return salad with promotion")
    public void shouldReturnSaladWithPromotion() {
        final String productName = "Amazing Salad!";

        BasketResponse.Content content =
                basketResponse.getContents().stream()
                        .filter((c) -> c.getProductName().equals(productName)).findFirst().orElse(null);

        assertThat(content).isNotNull();
        assertThat(content.getProductName()).isEqualTo(productName);
    }

    private String endPoint() {
        return SERVER_URL + ":" + port;
    }
}
