import java.util.List;
import java.util.Scanner;

import drink.Drink;
import payment.CardPayment;
import payment.CashPayment;
import payment.PaymentMethod;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();
        List<Drink> drinks = menu.getDrinks();
        Order order = new Order();

        System.out.println("Welcome to the Coffee Shop!");

        // The user chooses drinks until he enters 0
        boolean ordering = true;
        while (ordering) {
            System.out.println();
            System.out.println("Menu:");

            // The menu is printed from the list, so it is always up to date
            for (int i = 0; i < drinks.size(); i++) {
                Drink drink = drinks.get(i);
                System.out.println((i + 1) + " - " + drink.getName() + " (" + drink.getPrice() + " $)");
            }
            System.out.println("0 - Finish order");
            System.out.print("Your choice: ");

            String input = scanner.nextLine();

            // Turn the text into a number (if the user typed letters, choice = -1)
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                choice = -1;
            }

            if (choice == 0) {
                ordering = false;
            } else if (choice >= 1 && choice <= drinks.size()) {
                Drink chosen = drinks.get(choice - 1);
                order.addDrink(chosen);
                System.out.println(chosen.getName() + " added.");
            } else {
                System.out.println("Wrong choice, try again.");
            }
        }

        // If the order is empty, there is nothing to pay
        if (order.getDrinks().isEmpty()) {
            System.out.println("Your order is empty. Goodbye!");
            return;
        }

        // The user chooses how to pay
        PaymentMethod payment = null;
        while (payment == null) {
            System.out.println();
            System.out.println("How do you want to pay?");
            System.out.println("1 - Cash");
            System.out.println("2 - Card");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                payment = new CashPayment();
            } else if (choice.equals("2")) {
                System.out.print("Enter card number: ");
                String cardNumber = scanner.nextLine();
                payment = new CardPayment(cardNumber);
            } else {
                System.out.println("Wrong choice, try again.");
            }
        }

        // Checkout (Cashier works with any PaymentMethod)
        System.out.println();
        Cashier cashier = new Cashier(payment, new ReceiptPrinter());
        cashier.checkout(order);

        scanner.close();
    }
}