package com.qikserve.challenge.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Basket {

    private int userId;
    private BigDecimal rawTotal = BigDecimal.ZERO;
    private BigDecimal totalPromos = BigDecimal.ZERO;
    private BigDecimal totalPayable = BigDecimal.ZERO;

    private List<Content> contents = new ArrayList<>();

    @Getter
    @Setter
    @Builder
    public static class Content {
        private int amount;
        private String productId;
        private BigDecimal price;
        private String productName;
    }
}
