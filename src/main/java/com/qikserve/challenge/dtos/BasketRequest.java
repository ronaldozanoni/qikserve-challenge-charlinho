package com.qikserve.challenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketRequest {

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

    @NotNull
    @NotBlank
    @JsonProperty("product_id")
    private String productId;

    @NotNull
    @NotBlank
    private int amount;
}
