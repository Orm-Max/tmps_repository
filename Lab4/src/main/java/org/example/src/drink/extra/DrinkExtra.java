package org.example.src.drink.extra;

import org.example.src.order.OrderItem;

public abstract class DrinkExtra implements OrderItem {
    private final OrderItem drink;

    protected DrinkExtra(OrderItem drink) {
        this.drink = drink;
    }

    @Override
    public String getDescription() {
        return drink.getDescription();
    }

    @Override
    public double getPrice() {
        return drink.getPrice();
    }
}
