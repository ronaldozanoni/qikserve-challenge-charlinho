package com.qikserve.challenge.controllers;

import com.qikserve.challenge.dtos.BasketRequest;
import com.qikserve.challenge.dtos.BasketResponse;
import com.qikserve.challenge.dtos.CheckoutRequest;
import com.qikserve.challenge.dtos.CheckoutResponse;
import com.qikserve.challenge.models.Basket;
import com.qikserve.challenge.services.BasketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(value = "Basket")
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    private BasketService basketService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add item in basket")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Add item with success"),
            @ApiResponse(code = 500, message = "Exception on add item"),
    })
    public BasketResponse addItem(@RequestBody BasketRequest basketRequest) {
        return BasketResponse.parse(basketService.addItem(basketRequest));
    }

    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Finish request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Finished request with success"),
            @ApiResponse(code = 500, message = "Exception on finish request"),
    })
    public CheckoutResponse checkout(@RequestBody CheckoutRequest checkoutRequest) {
        Basket basket = basketService.checkout(checkoutRequest.getUserId());

        return CheckoutResponse.builder()
                    .totalPaid(basket.getTotalPayable())
                    .message("Order placed successfully!")
                .build();
    }
}
