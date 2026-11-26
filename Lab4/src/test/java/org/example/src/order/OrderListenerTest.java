package org.example.src.order;

import java.util.ArrayList;
import java.util.List;

import org.example.src.command.AddItemCommand;
import org.example.src.command.OrderHistory;
import org.example.src.drink.Espresso;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderListenerTest {
    @Test
    void commandAndUndoNotifyBothListenersAfterChangingTheCart() {
        Order order = new Order();
        OrderHistory history = new OrderHistory();
        Espresso espresso = new Espresso();
        List<OrderItem> changedItems = new ArrayList<>();
        List<Integer> itemCounts = new ArrayList<>();
        List<Boolean> additions = new ArrayList<>();
        order.subscribe((cart, item, added) -> changedItems.add(item));
        order.subscribe((cart, item, added) -> {
            itemCounts.add(cart.getItems().size());
            additions.add(added);
        });

        history.execute(new AddItemCommand(order, espresso));
        history.undo();

        assertEquals(List.of(espresso, espresso), changedItems);
        assertEquals(List.of(1, 0), itemCounts);
        assertEquals(List.of(true, false), additions);
    }

    @Test
    void unsubscribedListenerStopsReceivingChanges() {
        Order order = new Order();
        List<Boolean> firstEvents = new ArrayList<>();
        List<Boolean> secondEvents = new ArrayList<>();
        OrderListener first = (cart, item, added) -> firstEvents.add(added);
        order.subscribe(first);
        order.subscribe((cart, item, added) -> secondEvents.add(added));
        order.addItem(new Espresso());

        order.unsubscribe(first);
        order.removeLastItem();

        assertEquals(List.of(true), firstEvents);
        assertEquals(List.of(true, false), secondEvents);
    }
}
