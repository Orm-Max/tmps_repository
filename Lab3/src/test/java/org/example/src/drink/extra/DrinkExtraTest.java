package org.example.src.drink.extra;

import org.example.src.drink.Espresso;
import org.example.src.order.OrderItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DrinkExtraTest {
    @Test
    void stacksTwoExtrasWithoutChangingBaseDrink() {
        OrderItem espresso = new Espresso();
        OrderItem drink = new Cinnamon(new Syrup(espresso));

        assertEquals(3.50, drink.getPrice());
        assertEquals("Espresso (SMALL) + Syrup + Cinnamon", drink.getDescription());
        assertEquals(2.50, espresso.getPrice());
        assertEquals("Espresso (SMALL)", espresso.getDescription());
    }

    @Test
    void stacksExtrasInReverseOrderAtTheSamePrice() {
        OrderItem espresso = new Espresso();
        OrderItem drink = new Syrup(new Cinnamon(espresso));

        assertEquals(3.50, drink.getPrice());
        assertEquals("Espresso (SMALL) + Cinnamon + Syrup", drink.getDescription());
    }
}
