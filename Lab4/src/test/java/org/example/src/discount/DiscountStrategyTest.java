package org.example.src.discount;

import org.example.src.drink.Espresso;
import org.example.src.drink.Latte;
import org.example.src.drink.extra.Syrup;
import org.example.src.order.CoffeeCombo;
import org.example.src.order.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountStrategyTest {
    @Test
    void regularPriceUsesTheFullSubtotal() {
        assertEquals(6.00, new RegularPrice().calculateTotal(6.00));
    }

    @Test
    void loyaltyDiscountRoundsTheTotalToCents() {
        assertEquals(9.04, new LoyaltyDiscount().calculateTotal(10.04));
    }

    @Test
    void discountCanChangeOnTheSameOrderWithACombo() {
        Order order = new Order();
        order.addItem(new CoffeeCombo(new Syrup(new Espresso()), new Latte()));
        assertEquals(6.00, order.getTotal());

        order.setDiscount(new LoyaltyDiscount());
        assertEquals(6.00, order.getSubtotal());
        assertEquals(5.40, order.getTotal());

        order.setDiscount(new RegularPrice());
        assertEquals(6.00, order.getTotal());
    }
}
