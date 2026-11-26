package org.example.src.shop;

import java.util.Locale;

import org.example.src.order.Order;
import org.example.src.order.OrderItem;
import org.example.src.order.OrderListener;

public class CartSummary implements OrderListener {
    @Override
    public void itemChanged(Order order, OrderItem item, boolean added) {
        System.out.printf(Locale.US, "Cart: %d item(s), %.2f $%n",
                order.getItems().size(), order.getSubtotal());
    }
}
