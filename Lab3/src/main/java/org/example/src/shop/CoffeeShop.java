package org.example.src.shop;

import org.example.src.drink.Drink;
import org.example.src.order.Order;
import org.example.src.payment.PaymentCreator;

public class CoffeeShop {
    private final Menu menu = Menu.getInstance();
    private final ReceiptPrinter printer;

    public CoffeeShop() {
        this(new ReceiptPrinter());
    }

    CoffeeShop(ReceiptPrinter printer) {
        this.printer = printer;
    }

    public int getDrinkCount() {
        return menu.getDrinkCount();
    }

    public Drink chooseDrink(int number) {
        return menu.copyDrink(number - 1);
    }

    public String getDrinkLabel(int number) {
        return menu.getDrinkLabel(number - 1);
    }

    public void checkout(Order order, PaymentCreator paymentCreator) {
        Cashier cashier = new Cashier(paymentCreator.createPayment(), printer);
        cashier.checkout(order);
    }
}
