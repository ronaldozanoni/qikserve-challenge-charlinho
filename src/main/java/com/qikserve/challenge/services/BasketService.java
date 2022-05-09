package com.qikserve.challenge.services;

import com.qikserve.challenge.dtos.BasketRequest;
import com.qikserve.challenge.enums.PromotionType;
import com.qikserve.challenge.models.Basket;
import com.qikserve.challenge.models.Product;
import com.qikserve.challenge.models.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BasketService {

    @Autowired
    private ProductService productService;

    private final Basket basket = new Basket();

    public Basket addItem(BasketRequest basketRequest) {

        if (!canAddItem(basket.getUserId())) {
            //TODO can not add Exception
        }

        int amount = basketRequest.getAmount();
        Product product = productService.getProduct(basketRequest.getProductId());

        //TODO NOT FOUND EXCEPTION

        if (!product.getPromotions().isEmpty()) {
            applyPromotionIfAny(product, amount);
        }

        Optional<Basket.Content> content =
            basket.getContents().stream().filter(
                (c) -> c.getProductId().equals(basketRequest.getProductId())
            ).findFirst();

        if (content.isPresent()) {
            content.get().setAmount(content.get().getAmount() + basketRequest.getAmount());

            basket.setTotalPromos(basket.getTotalPromos().add(BigDecimal.ZERO));
            basket.setRawTotal(basket.getRawTotal().add(product.getPrice().multiply(BigDecimal.valueOf(basketRequest.getAmount()))));
            basket.setTotalPayable(basket.getTotalPayable().add(product.getPrice()).multiply(BigDecimal.valueOf(basketRequest.getAmount())));
        } else {
            basket.getContents().add(addContent(product, amount));
        }

        return basket;
    }

    private boolean canAddItem(Integer userId) {
        return basket.getUserId() != userId;
    }

    private Basket.Content addContent(Product product, int amount) {
        return Basket.Content.builder()
                    .productId(product.getId())
                    .price(product.getPrice())
                    .productName(product.getName())
                    .amount(amount)
                .build();
    }

    private void applyPromotionIfAny(Product product, int amount) {
        Optional<Promotion> promotion = product.getPromotions().stream()
            .filter((p -> amount >= p.getRequiredQty())).findFirst();

        if (!promotion.isPresent()) {
            basket.setRawTotal(basket.getRawTotal().add(product.getPrice()));
            basket.setTotalPromos(basket.getTotalPromos().add(BigDecimal.ZERO));
            basket.setTotalPayable(basket.getTotalPayable().add(product.getPrice()));
            return;
        }

        if (PromotionType.BUY_X_GET_Y_FREE.equals(promotion.get().getType())) {
            basket.setRawTotal(product.getPrice().multiply(BigDecimal.valueOf(amount)));
            basket.setTotalPromos(basket.getTotalPromos().subtract(product.getPrice().multiply(BigDecimal.valueOf(promotion.get().getFreeQty()))).abs());
            basket.setTotalPayable(basket.getRawTotal().subtract(basket.getTotalPromos()));
        }
    }
}
