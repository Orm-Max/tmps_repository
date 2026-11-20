package org.example.src.drink;

import org.example.src.order.OrderItem;

public abstract class Drink implements OrderItem {
    private final String name;
    private final double price;
    private Size size = Size.SMALL;

    public Drink(String name, double price) {
        this.name = name;
        this.price = price;
    }

    protected Drink(Drink other) {
        this.name = other.name;
        this.price = other.price;
        this.size = other.size;
    }

    public abstract Drink copy();

    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price + size.getExtraPrice();
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public String getDescription() {
        return name + " (" + size + ")";
    }
}
