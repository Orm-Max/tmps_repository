package org.example.src.drink.extra;

import org.example.src.order.OrderItem;

public class Syrup extends DrinkExtra {
    public Syrup(OrderItem drink) {
        super(drink);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Syrup";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 0.50;
    }
}
