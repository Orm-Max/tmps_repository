package org.example.src.discount;

public class LoyaltyDiscount implements DiscountStrategy {
    @Override
    public double calculateTotal(double subtotal) {
        return Math.round(subtotal * 0.90 * 100) / 100.0;
    }
}
