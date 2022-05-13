package com.qikserve.challenge.services;

import com.qikserve.challenge.dtos.BasketRequest;
import com.qikserve.challenge.enums.PromotionType;
import com.qikserve.challenge.exceptions.NotFoundException;
import com.qikserve.challenge.exceptions.ServerException;
import com.qikserve.challenge.exceptions.UnauthorizedException;
import com.qikserve.challenge.models.Basket;
import com.qikserve.challenge.models.Product;
import com.qikserve.challenge.models.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BasketService {

    @Autowired
    private ProductService productService;

    private Basket basket = new Basket();

    public Basket addItem(BasketRequest basketRequest) {

        if (basket.getUserId() != null && !basket.getUserId().equals(basketRequest.getUserId())) {
            throw new UnauthorizedException("User in basket is different!");
        }

        int amount = basketRequest.getAmount();
        String promotionCode = basketRequest.getPromotionCode();
        Product product = productService.getProduct(basketRequest.getProductId());

        basket.setUserId(basketRequest.getUserId());

        if (product == null) {
            throw new NotFoundException("Product not found!");
        }

        if (!product.getPromotions().isEmpty()) {
            applyPromotionIfAny(product, amount, basketRequest.getPromotionCode());
        }

        Basket.Content productFound = getContentByProductId(product.getId());

        if (productFound != null) {
            productFound.setAmount(productFound.getAmount() + amount);
        } else {
            basket.getContents().add(addContent(product, amount));
        }

        return basket;
    }

    public Basket checkout(int userId) {
        if (basket.getUserId() == null) {
            throw new ServerException("Basket is null, please add some item in basket");
        }

        if (basket.getUserId() != userId) {
            throw new UnauthorizedException("User in basket is different!");
        }

        Basket checkout = basket;

        clean();

        return checkout;
    }

    private void clean() {
        basket = new Basket();
    }

    private Basket.Content addContent(Product product, int amount) {
        return Basket.Content.builder()
                    .productId(product.getId())
                    .price(product.getPrice())
                    .productName(product.getName())
                    .amount(amount)
                .build();
    }

    private void applyPromotionIfAny(Product product, int amount, String promotionCode) {
        Promotion promotion = product.getPromotions().stream()
            .filter((p -> p.getId().equals(promotionCode)))
                .findFirst().orElse(new Promotion());

        if (PromotionType.FLAT_PERCENT.equals(promotion.getType())) {
            basket.setRawTotal(basket.getRawTotal().add(product.getPrice().multiply(BigDecimal.valueOf(amount))));
            basket.setTotalPromos(basket.getTotalPromos().add(BigDecimal.valueOf((float) promotion.getAmount() / 100).multiply(BigDecimal.valueOf(amount))));
            basket.setTotalPayable(basket.getRawTotal().subtract(product.getPrice().multiply(basket.getTotalPromos())));
            return;
        }

        if (PromotionType.BUY_X_GET_Y_FREE.equals(promotion.getType())) {
            if (amount >= promotion.getRequiredQty()) {
                basket.setRawTotal(basket.getRawTotal().add(product.getPrice().multiply(BigDecimal.valueOf(amount))));
                basket.setTotalPromos(basket.getTotalPromos().add(product.getPrice().multiply(BigDecimal.valueOf(promotion.getFreeQty()))).abs());
                basket.setTotalPayable(basket.getRawTotal().subtract(basket.getTotalPromos()));
                return;
            }
        }

        if (PromotionType.QTY_BASED_PRICE_OVERRIDE.equals(promotion.getType())) {
            if (amount >= promotion.getRequiredQty()) {
                basket.setRawTotal(basket.getRawTotal().add(promotion.getPrice().multiply(BigDecimal.valueOf(amount))));
                basket.setTotalPromos(promotion.getPrice().add(product.getPrice().multiply(BigDecimal.valueOf(promotion.getAmount()))));
                basket.setTotalPayable(basket.getRawTotal().subtract(basket.getTotalPromos()));
                return;
            }
        }

        basket.setRawTotal(basket.getRawTotal().add(product.getPrice()));
        basket.setTotalPromos(basket.getTotalPromos().add(BigDecimal.ZERO));
        basket.setTotalPayable(basket.getTotalPayable().add(product.getPrice()));
    }

    private Basket.Content getContentByProductId(String productId) {
        return basket.getContents().stream().filter(
                (c) -> c.getProductId().equals(productId)
        ).findFirst().orElse(null);
    }
}
