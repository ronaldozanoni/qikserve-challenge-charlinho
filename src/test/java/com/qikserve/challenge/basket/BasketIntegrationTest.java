package com.qikserve.challenge.basket;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/product",
        plugin = {"pretty", "html:target/cucumber/product"},
        extraGlue = "com.qikserve.challenge.common")
public class BasketIntegrationTest {
}
