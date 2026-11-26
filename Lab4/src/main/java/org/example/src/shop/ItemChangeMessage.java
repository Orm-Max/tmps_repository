package org.example.src.shop;

import org.example.src.order.Order;
import org.example.src.order.OrderItem;
import org.example.src.order.OrderListener;

public class ItemChangeMessage implements OrderListener {
    @Override
    public void itemChanged(Order order, OrderItem item, boolean added) {
        String action = added ? " added." : " removed.";
        System.out.println(item.getDescription() + action);
    }
}
