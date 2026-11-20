package org.example.src.shop;

import org.example.src.order.Order;
import org.example.src.order.OrderItem;

public class ReceiptPrinter {

    public void print(Order order) {
        System.out.println("===== COFFEE SHOP RECEIPT =====");
        for (OrderItem item : order.getItems()) {
            System.out.println(item.getDescription() + " - " + item.getPrice() + " $");
        }
        System.out.println("-------------------------------");
        System.out.println("TOTAL: " + order.getTotal() + " $");
        System.out.println("===============================");
    }
}
