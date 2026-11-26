package org.example.src.order;

public interface OrderListener {
    void itemChanged(Order order, OrderItem item, boolean added);
}
