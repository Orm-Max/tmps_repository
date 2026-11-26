package org.example.src.shop;

import org.example.src.order.Order;
import org.example.src.payment.PaymentMethod;

public class Cashier {
    private PaymentMethod paymentMethod;
    private ReceiptPrinter printer;

    public Cashier(PaymentMethod paymentMethod, ReceiptPrinter printer) {
        this.paymentMethod = paymentMethod;
        this.printer = printer;
    }

    public void checkout(Order order) {
        paymentMethod.pay(order.getTotal());
        printer.print(order);
    }
}
