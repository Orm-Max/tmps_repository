package org.example.src.shop;

import java.util.ArrayList;
import java.util.List;

import org.example.src.drink.Espresso;
import org.example.src.drink.Latte;
import org.example.src.drink.extra.Syrup;
import org.example.src.order.CoffeeCombo;
import org.example.src.order.Order;
import org.example.src.payment.PaymentCreator;
import org.example.src.payment.PaymentMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class CoffeeShopTest {
    @Test
    void checkoutChargesTheTotalForAComboWithAnExtra() {
        CoffeeShop shop = new CoffeeShop();
        Order order = new Order();
        order.addItem(new CoffeeCombo(new Syrup(new Espresso()), new Latte()));
        List<Double> chargedAmounts = new ArrayList<>();

        shop.checkout(order, paymentCreator(amount -> chargedAmounts.add(amount)));

        assertEquals(List.of(6.00), chargedAmounts);
    }

    @Test
    void checkoutPrintsTheOriginalOrderAfterPayment() {
        List<String> steps = new ArrayList<>();
        Order order = new Order();
        order.addItem(new Espresso());
        ReceiptPrinter printer = new ReceiptPrinter() {
            @Override
            public void print(Order printedOrder) {
                assertSame(order, printedOrder);
                steps.add("receipt");
            }
        };
        CoffeeShop shop = new CoffeeShop(printer);

        shop.checkout(order, paymentCreator(amount -> steps.add("payment")));

        assertEquals(List.of("payment", "receipt"), steps);
    }

    private PaymentCreator paymentCreator(PaymentMethod method) {
        return new PaymentCreator() {
            @Override
            public PaymentMethod createPayment() {
                return method;
            }
        };
    }
}
