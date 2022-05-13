Feature: Basket functionalities

  Scenario: Adding item in basket
    When user 1 add 1 item in basket
    Then should return item in contents list

  Scenario: Adding burger in basket
    When user add PWWe3w1SDU in basket
    Then should return added item

  Scenario: Adding burger in basket with promotion
    When user add items in basket with promotion code ZRAwbsO2qM
    Then should return two burgers with promotion

  Scenario: User doing checkout
    When user do checkout
    Then should return total paid

  Scenario: Adding pizza in basket with promotion
    When user add pizza in basket with promotion code ibt3EEYczW
    Then should return pizza with promotion

  Scenario: Adding salad in basket with promotion
    When user add salad in basket with promotion code Gm1piPn7Fg
    Then should return salad with promotion