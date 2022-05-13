package com.qikserve.challenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qikserve.challenge.models.Basket;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponse {

    private String message;

    @JsonProperty("total_paid")
    private BigDecimal totalPaid;

    public static CheckoutResponse parse(Basket basket) {
        return CheckoutResponse.builder()
                .totalPaid(basket.getTotalPayable())
                .message("Order placed successfully!")
            .build();
    }
}


