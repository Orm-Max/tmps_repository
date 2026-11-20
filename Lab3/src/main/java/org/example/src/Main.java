package org.example.src;

import java.util.Scanner;

import org.example.src.drink.Drink;
import org.example.src.drink.Size;
import org.example.src.drink.extra.Cinnamon;
import org.example.src.drink.extra.Syrup;
import org.example.src.order.CoffeeCombo;
import org.example.src.order.Order;
import org.example.src.order.OrderItem;
import org.example.src.payment.CardPaymentCreator;
import org.example.src.payment.CashPaymentCreator;
import org.example.src.payment.PaymentCreator;
import org.example.src.shop.CoffeeShop;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            CoffeeShop shop = new CoffeeShop();
            Order order = new Order();
            int comboNumber = shop.getDrinkCount() + 1;

            System.out.println("Welcome to the Coffee Shop!");

            while (true) {
                System.out.println("\nMenu:");
                printDrinks(shop);
                System.out.println(comboNumber + " - Coffee Combo (any two drinks, "
                        + CoffeeCombo.DISCOUNT + " $ off)");
                System.out.println("0 - Finish order");

                int choice = readChoice(scanner, 0, comboNumber);
                if (choice == 0) {
                    break;
                }

                OrderItem item;
                if (choice == comboNumber) {
                    item = chooseCombo(scanner, shop);
                } else {
                    item = customizeDrink(scanner, shop.chooseDrink(choice));
                }
                order.addItem(item);
                System.out.println(item.getDescription() + " added.");
            }

            if (order.getItems().isEmpty()) {
                System.out.println("Your order is empty. Goodbye!");
                return;
            }

            shop.checkout(order, choosePayment(scanner));
        }
    }

    private static void printDrinks(CoffeeShop shop) {
        for (int number = 1; number <= shop.getDrinkCount(); number++) {
            System.out.println(number + " - " + shop.getDrinkLabel(number));
        }
    }

    private static OrderItem chooseCombo(Scanner scanner, CoffeeShop shop) {
        System.out.println("Choose the first drink for the combo:");
        printDrinks(shop);
        OrderItem first = customizeDrink(scanner,
                shop.chooseDrink(readChoice(scanner, 1, shop.getDrinkCount())));

        System.out.println("Choose the second drink for the combo:");
        printDrinks(shop);
        OrderItem second = customizeDrink(scanner,
                shop.chooseDrink(readChoice(scanner, 1, shop.getDrinkCount())));

        return new CoffeeCombo(first, second);
    }

    private static OrderItem customizeDrink(Scanner scanner, Drink drink) {
        System.out.println("Size: 1 - Small, 2 - Medium (+0.5 $), 3 - Large (+1.0 $)");
        int size = readChoice(scanner, 1, 3);
        if (size == 2) {
            drink.setSize(Size.MEDIUM);
        } else if (size == 3) {
            drink.setSize(Size.LARGE);
        }

        OrderItem item = drink;
        while (true) {
            System.out.println("Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done");
            int extra = readChoice(scanner, 0, 2);
            if (extra == 0) {
                return item;
            }
            if (extra == 1) {
                item = new Syrup(item);
            } else {
                item = new Cinnamon(item);
            }
        }
    }

    private static PaymentCreator choosePayment(Scanner scanner) {
        System.out.println("\nHow do you want to pay?");
        System.out.println("1 - Cash");
        System.out.println("2 - Card");
        if (readChoice(scanner, 1, 2) == 1) {
            return new CashPaymentCreator();
        }

        System.out.print("Enter card number: ");
        return new CardPaymentCreator(scanner.nextLine());
    }

    private static int readChoice(Scanner scanner, int min, int max) {
        while (true) {
            System.out.print("Your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (NumberFormatException e) {
                // Keep asking if the input is not a number.
            }
            System.out.println("Wrong choice, try again.");
        }
    }
}
