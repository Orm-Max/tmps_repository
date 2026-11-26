package org.example.src.order;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class CoffeeCombo implements OrderItem {
    public static final double DISCOUNT = 0.50;

    private final List<OrderItem> items;

    public CoffeeCombo(OrderItem first, OrderItem second) {
        items = Arrays.asList(first, second);
    }

    @Override
    public String getDescription() {
        StringJoiner description = new StringJoiner(", ", "Coffee Combo [", "]");
        for (OrderItem item : items) {
            description.add(item.getDescription());
        }
        return description + " (discount " + DISCOUNT + " $)";
    }

    @Override
    public double getPrice() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getPrice();
        }
        return total - DISCOUNT;
    }
}
