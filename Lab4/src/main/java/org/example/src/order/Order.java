package org.example.src.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.example.src.discount.DiscountStrategy;
import org.example.src.discount.RegularPrice;

public class Order {
    private final List<OrderItem> items = new ArrayList<>();
    private final List<OrderListener> listeners = new ArrayList<>();
    private DiscountStrategy discount = new RegularPrice();

    public void addItem(OrderItem item) {
        items.add(item);
        notifyListeners(item, true);
    }

    public void removeLastItem() {
        OrderItem item = items.remove(items.size() - 1);
        notifyListeners(item, false);
    }

    public void subscribe(OrderListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(OrderListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(OrderItem item, boolean added) {
        for (OrderListener listener : new ArrayList<>(listeners)) {
            listener.itemChanged(this, item, added);
        }
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public double getTotal() {
        return discount.calculateTotal(getSubtotal());
    }

    public void setDiscount(DiscountStrategy discount) {
        this.discount = discount;
    }

    public double getSubtotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getPrice();
        }
        return total;
    }
}
