package org.example.src.drink.extra;

import org.example.src.order.OrderItem;

public class Cinnamon extends DrinkExtra {
    public Cinnamon(OrderItem drink) {
        super(drink);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Cinnamon";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 0.50;
    }
}
