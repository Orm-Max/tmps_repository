import java.util.List;
import java.util.Scanner;

import drink.Drink;
import drink.Size;
import payment.CardPaymentCreator;
import payment.CashPaymentCreator;
import payment.PaymentCreator;
import payment.PaymentMethod;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menu = Menu.getInstance();
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
                System.out.println((i + 1) + " - " + drink.getName() + " (" + drink.getPrice() + " $ for small)");
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
                // We take a copy of the drink and change only the copy
                Drink chosen = drinks.get(choice - 1).copy();
                chosen.setSize(chooseSize(scanner));
                addExtras(scanner, chosen);

                order.addDrink(chosen);
                System.out.println(chosen.getDescription() + " added.");
            } else {
                System.out.println("Wrong choice, try again.");
            }
        }

        // If the order is empty, there is nothing to pay
        if (order.getDrinks().isEmpty()) {
            System.out.println("Your order is empty. Goodbye!");
            return;
        }

        // The user chooses how to pay.
        PaymentCreator creator = null;
        while (creator == null) {
            System.out.println();
            System.out.println("How do you want to pay?");
            System.out.println("1 - Cash");
            System.out.println("2 - Card");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                creator = new CashPaymentCreator();
            } else if (choice.equals("2")) {
                System.out.print("Enter card number: ");
                String cardNumber = scanner.nextLine();
                creator = new CardPaymentCreator(cardNumber);
            } else {
                System.out.println("Wrong choice, try again.");
            }
        }
        PaymentMethod payment = creator.createPayment();

        // Checkout (Cashier works with any PaymentMethod)
        System.out.println();
        Cashier cashier = new Cashier(payment, new ReceiptPrinter());
        cashier.checkout(order);

        scanner.close();
    }

    // Asks the user for the drink size until the answer is correct
    private static Size chooseSize(Scanner scanner) {
        while (true) {
            System.out.println("Size: 1 - Small, 2 - Medium (+0.5 $), 3 - Large (+1.0 $)");
            System.out.print("Your choice: ");
            String input = scanner.nextLine();

            if (input.equals("1")) {
                return Size.SMALL;
            } else if (input.equals("2")) {
                return Size.MEDIUM;
            } else if (input.equals("3")) {
                return Size.LARGE;
            }
            System.out.println("Wrong choice, try again.");
        }
    }

    private static void addExtras(Scanner scanner, Drink drink) {
        System.out.println("You can add extras (0.5 $ each), for example: Syrup, Cinnamon.");
        boolean adding = true;
        while (adding) {
            System.out.print("Extra (press Enter to finish): ");
            String extra = scanner.nextLine().trim();

            if (extra.isEmpty()) {
                adding = false;
            } else {
                drink.addExtra(extra);
                System.out.println(extra + " added.");
            }
        }
    }
}
