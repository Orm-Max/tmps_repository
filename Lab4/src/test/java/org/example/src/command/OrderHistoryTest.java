package org.example.src.command;

import java.util.List;

import org.example.src.drink.Espresso;
import org.example.src.drink.Latte;
import org.example.src.order.CoffeeCombo;
import org.example.src.order.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderHistoryTest {
    @Test
    void undoRestoresTheCartInReverseOrder() {
        Order order = new Order();
        OrderHistory history = new OrderHistory();
        Espresso espresso = new Espresso();
        CoffeeCombo combo = new CoffeeCombo(new Espresso(), new Latte());

        history.execute(new AddItemCommand(order, espresso));
        history.execute(new AddItemCommand(order, combo));
        assertEquals(List.of(espresso, combo), order.getItems());

        assertTrue(history.undo());
        assertEquals(List.of(espresso), order.getItems());
        assertEquals(2.50, order.getTotal());
        assertTrue(history.undo());
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    void undoWithNoCommandsLeavesTheCartEmpty() {
        Order order = new Order();
        OrderHistory history = new OrderHistory();

        assertFalse(history.undo());
        assertTrue(order.getItems().isEmpty());
    }
}
