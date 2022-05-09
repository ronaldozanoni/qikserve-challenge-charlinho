package com.qikserve.challenge.controllers;

import com.qikserve.challenge.dtos.BasketRequest;
import com.qikserve.challenge.dtos.BasketResponse;
import com.qikserve.challenge.services.BasketService;
import io.swagger.annotations.Api;
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
    public BasketResponse addItem(@RequestBody BasketRequest basketRequest) {
        return BasketResponse.parse(basketService.addItem(basketRequest));
    }
}
