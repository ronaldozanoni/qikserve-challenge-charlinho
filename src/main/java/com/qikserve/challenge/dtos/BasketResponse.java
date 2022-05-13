package com.qikserve.challenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qikserve.challenge.models.Basket;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BasketResponse {

    @JsonProperty("raw_value")
    private BigDecimal rawValue;

    @JsonProperty("total_promos")
    private BigDecimal totalPromos;

    @JsonProperty("total_payable")
    private BigDecimal totalPayable;

    @JsonProperty("contents")
    private List<Content> contents;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        private int amount;
        private BigDecimal price;

        @JsonProperty("product_name")
        private String productName;
    }

    public static BasketResponse parse(Basket basket) {
        return BasketResponse.builder()
                .rawValue(basket.getRawTotal())
                .totalPromos(basket.getTotalPromos())
                .totalPayable(basket.getTotalPayable())
                .contents(
                        basket.getContents().stream().map((item) ->
                                BasketResponse.Content.builder()
                                        .price(item.getPrice())
                                        .amount(item.getAmount())
                                        .productName(item.getProductName())
                                    .build()
                        ).collect(Collectors.toList()))
                .build();
    }
}
