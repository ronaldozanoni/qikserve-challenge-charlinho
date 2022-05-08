package com.qikserve.challenge.models;

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
    private Integer amount;
    private Integer freeQty;
    private PromotionType type;
    private Integer requiredQty;
}
