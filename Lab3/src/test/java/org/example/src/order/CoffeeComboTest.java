package org.example.src.order;

import org.example.src.drink.Espresso;
import org.example.src.drink.Latte;
import org.example.src.drink.extra.Syrup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CoffeeComboTest {
    @Test
    void combinesDrinkPricesAndAppliesDiscount() {
        OrderItem combo = new CoffeeCombo(new Syrup(new Espresso()), new Latte());

        assertEquals(6.00, combo.getPrice());
        assertTrue(combo.getDescription().contains("Espresso (SMALL) + Syrup, Latte (SMALL)"));
    }

    @Test
    void orderAcceptsBothSingleDrinkAndComboAsItems() {
        OrderItem drink = new Espresso();
        OrderItem combo = new CoffeeCombo(new Espresso(), new Latte());
        Order order = new Order();

        order.addItem(drink);
        order.addItem(combo);

        assertEquals(2, order.getItems().size());
        assertEquals(8.00, order.getTotal());
    }
}
