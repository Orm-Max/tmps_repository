package org.example.src.shop;

import java.util.Locale;

import org.example.src.order.Order;
import org.example.src.order.OrderItem;

public class ReceiptPrinter {

    public void print(Order order) {
        System.out.println("===== COFFEE SHOP RECEIPT =====");
        for (OrderItem item : order.getItems()) {
            System.out.println(item.getDescription() + " - " + item.getPrice() + " $");
        }
        System.out.println("-------------------------------");
        double subtotal = order.getSubtotal();
        double total = order.getTotal();
        if (total < subtotal) {
            System.out.printf(Locale.US, "SUBTOTAL: %.2f $%n", subtotal);
            System.out.printf(Locale.US, "DISCOUNT: %.2f $%n", subtotal - total);
        }
        System.out.printf(Locale.US, "TOTAL: %.2f $%n", total);
        System.out.println("===============================");
    }
}
