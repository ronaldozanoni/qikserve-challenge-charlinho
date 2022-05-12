package com.qikserve.challenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter
public class CheckoutResponse {

    private String message;

    @JsonProperty("total_paid")
    private BigDecimal totalPaid;
}
