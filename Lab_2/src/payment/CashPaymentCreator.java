package payment;

public class CashPaymentCreator extends PaymentCreator {
    @Override
    public PaymentMethod createPayment() {
        return new CashPayment();
    }
}
