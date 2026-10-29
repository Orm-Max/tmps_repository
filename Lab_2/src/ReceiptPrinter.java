import drink.Drink;

public class ReceiptPrinter {

    public void print(Order order) {
        System.out.println("===== COFFEE SHOP RECEIPT =====");
        for (Drink drink : order.getDrinks()) {
            System.out.println(drink.getDescription() + " - " + drink.getPrice() + " $");
        }
        System.out.println("-------------------------------");
        System.out.println("TOTAL: " + order.getTotal() + " $");
        System.out.println("===============================");
    }
}
