package org.example.src.command;

import org.example.src.order.Order;
import org.example.src.order.OrderItem;

public class AddItemCommand implements OrderCommand {
    private final Order order;
    private final OrderItem item;

    public AddItemCommand(Order order, OrderItem item) {
        this.order = order;
        this.item = item;
    }

    @Override
    public void execute() {
        order.addItem(item);
    }

    @Override
    public void undo() {
        order.removeLastItem();
    }
}
