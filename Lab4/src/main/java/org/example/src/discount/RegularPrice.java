package org.example.src.discount;

public class RegularPrice implements DiscountStrategy {
    @Override
    public double calculateTotal(double subtotal) {
        return subtotal;
    }
}
