package com.qikserve.challenge.dtos;

import com.qikserve.challenge.models.Product;
import com.qikserve.challenge.models.Promotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private String id;
    private String name;
    private BigDecimal price;

    public static List<ProductResponse> parse(List<Product> products) {
        return products.stream().map((product) ->
            ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .build()
            ).collect(Collectors.toList());
    }
}
