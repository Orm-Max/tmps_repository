package payment;

public class CardPaymentCreator extends PaymentCreator {
    private String cardNumber;

    public CardPaymentCreator(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public PaymentMethod createPayment() {
        return new CardPayment(cardNumber);
    }
}
