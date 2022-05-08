package com.qikserve.challenge.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * The type Product.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String id;
    private String name;
    private BigDecimal price;

    private List<Promotion> promotions;
}
