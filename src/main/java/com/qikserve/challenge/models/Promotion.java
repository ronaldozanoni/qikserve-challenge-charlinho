package com.qikserve.challenge.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qikserve.challenge.enums.PromotionType;
import lombok.*;

/**
 * The type Promotion.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {

    private String id;
    private int amount;

    @JsonProperty("free_qty")
    private int freeQty;
    private PromotionType type;

    @JsonProperty("required_qty")
    private int requiredQty;
}
