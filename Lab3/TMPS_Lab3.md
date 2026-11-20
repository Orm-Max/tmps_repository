# Lab 3: Structural Design Patterns

**MINISTRY OF EDUCATION, CULTURE AND RESEARCH**  
**OF THE REPUBLIC OF MOLDOVA**  

**Technical University of Moldova**  
**Faculty of Computers, Informatics and Microelectronics**  
**Department of Software Engineering and Automation**  


**MAXIM ORMANJI FAF-242**

Checked by: Istrati Daniel

Chișinău, 2026

---

## Table of Contents

- [Introduction](#introduction)
- [1. Theoretical Background](#1-theoretical-background)
  - [1.1 Structural Design Patterns](#11-structural-design-patterns)
  - [1.2 Patterns Selected for This Work](#12-patterns-selected-for-this-work)
- [2. Program and Results](#2-program-and-results)
  - [2.1 Project Description](#21-project-description)
  - [2.2 Applied Structural Patterns](#22-applied-structural-patterns)
  - [2.3 Source Code](#23-source-code)
  - [2.4 Program Output](#24-program-output)
- [Conclusion](#conclusion)
- [Bibliography](#bibliography)

---

## Introduction

When a program grows, the main question is no longer how to create objects, but how to *combine* them: how classes and objects are put together into larger structures, and how to do it without making the code rigid. Structural design patterns, described by the "Gang of Four" in 1994, answer this question. They show how to compose classes and objects into bigger structures while keeping these structures flexible and easy to change.

This laboratory work applies three structural patterns (Decorator, Composite and Facade) in the Java coffee shop application that was developed in Laboratory works No.1 and No.2. The creational patterns from the previous work (Singleton for `Menu`, Prototype for `Drink`, Factory Method for payments) are kept. In this work the extras of a drink (syrup and cinnamon) become decorators, a new "Coffee Combo" item is added to the order, and the console gets one simple entry point to the shop, `CoffeeShop`. The program is deliberately simple, so that each pattern can be seen clearly in a few short classes.

---

## 1. Theoretical Background

### 1.1 Structural Design Patterns

Structural design patterns are concerned with how classes and objects are composed to form larger structures. They use inheritance and composition to build new functionality from existing classes and objects, and they help to keep a structure flexible and efficient when its parts change. The Gang of Four describes seven structural patterns, which are summarised in Table 1.1.

*Table 1.1 – Structural design patterns*

| Pattern | Main idea |
| --- | --- |
| **Adapter** | Converts the interface of a class into another interface that the client expects, so classes with incompatible interfaces can work together |
| **Bridge** | Separates an abstraction from its implementation, so that both can vary independently |
| **Composite** | Composes objects into tree structures and lets clients treat individual objects and groups of objects in the same way |
| **Decorator** | Adds new responsibilities to an object dynamically, by wrapping it in another object with the same interface; a flexible alternative to subclassing |
| **Facade** | Provides one simple, unified interface to a set of interfaces in a subsystem, so the subsystem is easier to use |
| **Flyweight** | Shares many small objects efficiently, so that a large number of similar objects does not use too much memory |
| **Proxy** | Provides a substitute (placeholder) for another object and controls access to it |

### 1.2 Patterns Selected for This Work

Three patterns are demonstrated in this project:

- **Decorator** in the package `drink.extra`: syrup and cinnamon are decorators that wrap a drink (or another decorator). Each extra adds its own text to the description and its own price, and the extras can be added in any order.
- **Composite** in the class `CoffeeCombo`: two drinks with their own sizes and extras form a combo with a discount of 0.50 $. In an order, a combo and a single drink are used through the same interface, `OrderItem`.
- **Facade** in the class `CoffeeShop`: it gives the console access to the menu and to the checkout. The payment and the printing of the receipt are still done by the existing classes (`Cashier`, `PaymentMethod`, `ReceiptPrinter`).

Adapter, Bridge, Flyweight and Proxy are described in the theory above but are not demonstrated in this work.

---

## 2. Program and Results

### 2.1 Project Description

The application is a console program. The user sees a numbered menu with three drinks and one "Coffee Combo" item, chooses a drink, a size and extras, enters `0` to finish the order, chooses a payment method (cash or card), and receives a receipt. Invalid input is handled by repeating the question. The price of a drink is *base price + size price*, and each extra adds 0.5 $. The sizes are Small (+0.0 $), Medium (+0.5 $) and Large (+1.0 $). A combo consists of any two drinks and costs 0.5 $ less than the sum of its parts.

The project is organised in the packages `drink`, `drink.extra`, `order`, `payment` and `shop`, and consists of the classes listed in Table 2.1.

*Table 2.1 – Classes of the coffee shop project*

| Class / interface | Responsibility | Pattern |
| --- | --- | --- |
| `OrderItem` | Common interface of everything that can be put into an order: `getDescription()` and `getPrice()` | Decorator, Composite |
| `Drink` | Abstract drink (name, base price, size); implements `OrderItem` | Decorator (component), Composite (leaf), Prototype |
| `Espresso`, `Latte`, `Cappuccino` | Concrete drinks | Prototype |
| `DrinkExtra` | Abstract decorator: wraps an `OrderItem` and delegates to it | Decorator |
| `Syrup`, `Cinnamon` | Concrete decorators: add text and 0.50 $ | Decorator |
| `CoffeeCombo` | Group of two `OrderItem` objects with a discount | Composite |
| `Order` | Stores `OrderItem` objects and calculates the total | – (client of Composite) |
| `CoffeeShop` | Simple entry point to the menu and the checkout | Facade |
| `Menu` | Holds the only list of drinks | Singleton |
| `Cashier`, `ReceiptPrinter` | Take the payment and print the receipt | – (subsystem of Facade) |
| `PaymentCreator`, `CashPaymentCreator`, `CardPaymentCreator` | Create the payment method | Factory Method |
| `PaymentMethod`, `CashPayment`, `CardPayment` | Payment methods | – (subsystem of Facade) |
| `Size` | Enumeration of drink sizes | – |
| `Main` | Console input and output | – (client) |

The class structure of the three patterns is shown in three UML class diagrams.

![Figure 2.1 – Class diagram of Decorator](decorator.png)

*Figure 2.1 – Class diagram of the Decorator pattern*

Figure 2.1 shows the part of the system that uses the **Decorator** pattern. The interface `OrderItem` declares `getDescription()` and `getPrice()`. It is implemented by the abstract class `Drink` (the component that is decorated) and by the abstract decorator `DrinkExtra`. `DrinkExtra` keeps a reference to another `OrderItem` (the aggregation `drink`) and by default passes both calls to it. The concrete decorators `Syrup` and `Cinnamon` extend `DrinkExtra` and add their own text and price on top of the result of the wrapped object. The note at the bottom shows that the extras can be wrapped in any order.

![Figure 2.2 – Class diagram of Composite](composite.png)

*Figure 2.2 – Class diagram of the Composite pattern*

Figure 2.2 shows the part of the system that uses the **Composite** pattern. `OrderItem` is the common component. `Drink` is the leaf: a single drink. `CoffeeCombo` is the composite: it holds two `OrderItem` objects (the aggregation `items`, multiplicity 2) and also implements `OrderItem`. The class `Order` is the client: it keeps a list of `OrderItem` objects (0..*) and does not need to know whether an item is a single drink or a combo. The note at `CoffeeCombo` shows how the price is calculated: the sum of the prices of the items minus `DISCOUNT`.

![Figure 2.3 – Class diagram of Facade](facade.png)

*Figure 2.3 – Class diagram of the Facade pattern*

Figure 2.3 shows the part of the system that uses the **Facade** pattern. `Main` (the client) calls `CoffeeShop`. `CoffeeShop` is the facade: it uses `Menu` and `ReceiptPrinter` through its fields, creates a `Cashier` for the checkout and uses a `PaymentCreator` to get the payment method. These classes, together with `Drink`, `Order` and `PaymentMethod`, form the subsystem that is shown inside the frame "subsystem classes". The note at `Main` explains that `Main` still creates the `Order` and the `PaymentCreator` and passes them to `checkout()`.

### 2.2 Applied Structural Patterns

#### Decorator – package `drink.extra`

**Problem.** In Laboratory work No.2 the extras were stored in `Drink` as a list of strings, and every extra cost the same fixed price, `EXTRA_PRICE`. A new extra with another price or another behaviour (for example, a free extra or an extra that depends on the size) would require changes in the class `Drink` itself. The drink had to know about all possible extras.

**Solution.** All items of an order get a common interface, `OrderItem`. `Drink` implements it and no longer knows anything about extras. The abstract class `DrinkExtra` also implements `OrderItem` and wraps another `OrderItem`; by default it just passes the calls to the wrapped object. Each concrete extra overrides the methods and adds its part: the text in `getDescription()` and 0.50 $ in `getPrice()`. Because a decorator is itself an `OrderItem`, it can be wrapped by another decorator, so any number of extras can be added.

```java
public interface OrderItem {
    String getDescription();
    double getPrice();
}

public abstract class DrinkExtra implements OrderItem {
    private final OrderItem drink;

    protected DrinkExtra(OrderItem drink) {
        this.drink = drink;
    }

    @Override
    public String getDescription() {
        return drink.getDescription();
    }

    @Override
    public double getPrice() {
        return drink.getPrice();
    }
}

public class Syrup extends DrinkExtra {
    public Syrup(OrderItem drink) {
        super(drink);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Syrup";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 0.50;
    }
}
```

`Cinnamon` has exactly the same structure, with the text `" + Cinnamon"`. In `Main`, the extras are added in a loop: each choice wraps the current item in a new decorator.

```java
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
```

**Result.** `Drink` is simpler (it has no list of extras), and a new extra is a new class that does not change any existing class. The extras can be added in any order and any number of times. The price does not depend on the order, and the description shows the extras in the order in which they were added (see Scenario 2 in section 2.4).

#### Composite – class `CoffeeCombo`

**Problem.** The shop wants to sell a combo: two drinks, each with its own size and extras, for a lower price. The combo must be put into the same order as a single drink, and the class `Order` and the receipt must not have separate code for "a drink" and "a combo".

**Solution.** `CoffeeCombo` implements the same interface `OrderItem` as a single drink and as a decorated drink. It keeps two `OrderItem` objects. Its description is built from the descriptions of its items, and its price is the sum of their prices minus the constant `DISCOUNT`. `Order` stores `OrderItem` objects and sums their prices; it works in the same way for a drink, a decorated drink and a combo.

```java
public class CoffeeCombo implements OrderItem {
    public static final double DISCOUNT = 0.50;

    private final List<OrderItem> items;

    public CoffeeCombo(OrderItem first, OrderItem second) {
        items = Arrays.asList(first, second);
    }

    @Override
    public String getDescription() {
        StringJoiner description = new StringJoiner(", ", "Coffee Combo [", "]");
        for (OrderItem item : items) {
            description.add(item.getDescription());
        }
        return description + " (discount " + DISCOUNT + " $)";
    }

    @Override
    public double getPrice() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getPrice();
        }
        return total - DISCOUNT;
    }
}

public class Order {
    private final List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public double getTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getPrice();
        }
        return total;
    }
    // getItems() ...
}
```

In `Main`, the user configures two drinks one after another, and the combo is built from them:

```java
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
```

**Result.** A combo and a single drink are used through the same interface, so `Order`, `Cashier` and `ReceiptPrinter` did not need any special code for combos. The items of a combo can be decorated drinks, so Decorator and Composite work together. In this program a combo has exactly two items, which are given in the constructor; the classic version of the pattern also has methods to add and remove children, which are not needed here.

#### Facade – class `CoffeeShop`

**Problem.** In Laboratory work No.2, `Main` worked directly with several classes of the shop: it took the drinks from `Menu`, created `Cashier` and `ReceiptPrinter`, and called the checkout itself. The console code therefore knew how the shop is built inside, and any change in the internal classes could require a change in `Main`.

**Solution.** The class `CoffeeShop` gives the console a short list of operations: the number of drinks, the label of a drink, a copy of the chosen drink, and the checkout. Inside, it uses `Menu`, creates a `Cashier` with the payment method that is created by the `PaymentCreator`, and uses `ReceiptPrinter`. The facade does not do the payment or the printing itself: it only connects the existing classes.

```java
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
```

`Main` uses the facade for the menu and for the checkout:

```java
CoffeeShop shop = new CoffeeShop();
...
printDrinks(shop);                                  // getDrinkCount(), getDrinkLabel()
item = customizeDrink(scanner, shop.chooseDrink(choice));
...
shop.checkout(order, choosePayment(scanner));       // Cashier and ReceiptPrinter are hidden
```

**Result.** `Main` does not create `Cashier` and does not work with `Menu` directly. The classes of the subsystem (`Menu`, `Cashier`, `ReceiptPrinter`, `PaymentMethod`) are not changed and can still be used without the facade. Note that `Main` still creates the `Order`, the decorators and the `PaymentCreator` itself, because they depend on the user's input; the facade simplifies the access to the menu and to the checkout, and it does not hide the whole system.

*Table 2.2 – What each pattern gave to the project*

| Pattern | Before | After |
| --- | --- | --- |
| Decorator | Extras were a list of strings inside `Drink`, all with the same fixed price | `Drink` knows nothing about extras; each extra is a separate class and can be added in any order |
| Composite | Only single drinks could be in an order | A combo and a single drink are used through `OrderItem`; `Order` and `ReceiptPrinter` have no special code for combos |
| Facade | `Main` created `Cashier` and worked with `Menu` directly | `Main` calls four methods of `CoffeeShop`; the inner classes are hidden |

### 2.3 Source Code

The complete source code of the project is available in the GitHub repository [[1]](#bibliography). The most important parts are shown in section 2.2. The remaining class that has to be mentioned is `ReceiptPrinter`: it prints `OrderItem` objects, so it does not depend on the kind of item, and it was changed only in this respect:

```java
public class ReceiptPrinter {

    public void print(Order order) {
        System.out.println("===== COFFEE SHOP RECEIPT =====");
        for (OrderItem item : order.getItems()) {
            System.out.println(item.getDescription() + " - " + item.getPrice() + " $");
        }
        System.out.println("-------------------------------");
        System.out.println("TOTAL: " + order.getTotal() + " $");
        System.out.println("===============================");
    }
}
```

`Drink` implements `OrderItem` and no longer has a list of extras:

```java
public abstract class Drink implements OrderItem {
    private final String name;
    private final double price;
    private Size size = Size.SMALL;

    // constructors, copy() ...

    @Override
    public double getPrice() {
        return price + size.getExtraPrice();
    }

    @Override
    public String getDescription() {
        return name + " (" + size + ")";
    }
}
```

### 2.4 Program Output

All outputs in this section are the real console output of the program; the repeated menu is shortened with `...` to save space.

#### Scenario 1: A single drink without extras

The user orders one Espresso (Small, no extras) and pays in cash. The expected total is 2.5.

```
Welcome to the Coffee Shop!

Menu:
1 - Espresso (2.5 $ for small)
2 - Latte (3.5 $ for small)
3 - Cappuccino (3.0 $ for small)
4 - Coffee Combo (any two drinks, 0.5 $ off)
0 - Finish order
Your choice: 1
Size: 1 - Small, 2 - Medium (+0.5 $), 3 - Large (+1.0 $)
Your choice: 1
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 0
Espresso (SMALL) added.

Menu:
...
Your choice: 0

How do you want to pay?
1 - Cash
2 - Card
Your choice: 1
Paid 2.5 $ in cash.
===== COFFEE SHOP RECEIPT =====
Espresso (SMALL) - 2.5 $
-------------------------------
TOTAL: 2.5 $
===============================
```

#### Scenario 2: Decorator – extras in different order

The user orders a Medium Latte with Syrup and then Cinnamon, and a Large Cappuccino with Cinnamon and then Syrup. Both drinks have the same two extras, but they are added in the opposite order.

```
Menu:
...
Your choice: 2
Size: 1 - Small, 2 - Medium (+0.5 $), 3 - Large (+1.0 $)
Your choice: 2
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 1
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 2
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 0
Latte (MEDIUM) + Syrup + Cinnamon added.

Menu:
...
Your choice: 3
Size: 1 - Small, 2 - Medium (+0.5 $), 3 - Large (+1.0 $)
Your choice: 3
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 2
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 1
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 0
Cappuccino (LARGE) + Cinnamon + Syrup added.

Menu:
...
Your choice: 0

How do you want to pay?
1 - Cash
2 - Card
Your choice: 1
Paid 10.0 $ in cash.
===== COFFEE SHOP RECEIPT =====
Latte (MEDIUM) + Syrup + Cinnamon - 5.0 $
Cappuccino (LARGE) + Cinnamon + Syrup - 5.0 $
-------------------------------
TOTAL: 10.0 $
===============================
```

The calculation is checked in Table 2.3.

*Table 2.3 – Price calculation for Scenario 2*

| Item | Calculation | Price |
| --- | --- | --- |
| Latte (MEDIUM) + Syrup + Cinnamon | 3.5 + 0.5 (size) + 0.5 (Syrup) + 0.5 (Cinnamon) | 5.0 $ |
| Cappuccino (LARGE) + Cinnamon + Syrup | 3.0 + 1.0 (size) + 0.5 (Cinnamon) + 0.5 (Syrup) | 5.0 $ |
| **Total** | 5.0 + 5.0 | **10.0 $** |

The output shows that each extra adds its text to the description in the order of wrapping, and that every extra adds 0.5 $ regardless of this order.

#### Scenario 3: Composite – a combo and a single drink in one order

The user orders a Coffee Combo (a Small Espresso and a Medium Latte, no extras), then a Large Cappuccino with Syrup as a single drink, and pays in cash.

```
Menu:
...
Your choice: 4
Choose the first drink for the combo:
1 - Espresso (2.5 $ for small)
2 - Latte (3.5 $ for small)
3 - Cappuccino (3.0 $ for small)
Your choice: 1
Size: 1 - Small, 2 - Medium (+0.5 $), 3 - Large (+1.0 $)
Your choice: 1
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 0
Choose the second drink for the combo:
1 - Espresso (2.5 $ for small)
2 - Latte (3.5 $ for small)
3 - Cappuccino (3.0 $ for small)
Your choice: 2
Size: 1 - Small, 2 - Medium (+0.5 $), 3 - Large (+1.0 $)
Your choice: 2
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 0
Coffee Combo [Espresso (SMALL), Latte (MEDIUM)] (discount 0.5 $) added.

Menu:
...
Your choice: 3
Size: 1 - Small, 2 - Medium (+0.5 $), 3 - Large (+1.0 $)
Your choice: 3
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 1
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 0
Cappuccino (LARGE) + Syrup added.

Menu:
...
Your choice: 0

How do you want to pay?
1 - Cash
2 - Card
Your choice: 1
Paid 10.5 $ in cash.
===== COFFEE SHOP RECEIPT =====
Coffee Combo [Espresso (SMALL), Latte (MEDIUM)] (discount 0.5 $) - 6.0 $
Cappuccino (LARGE) + Syrup - 4.5 $
-------------------------------
TOTAL: 10.5 $
===============================
```

The calculation is checked in Table 2.4.

*Table 2.4 – Price calculation for Scenario 3*

| Item | Calculation | Price |
| --- | --- | --- |
| Coffee Combo [Espresso (SMALL), Latte (MEDIUM)] | 2.5 + (3.5 + 0.5) − 0.5 (discount) | 6.0 $ |
| Cappuccino (LARGE) + Syrup | 3.0 + 1.0 (size) + 0.5 (Syrup) | 4.5 $ |
| **Total** | 6.0 + 4.5 | **10.5 $** |

The receipt prints the combo and the single drink in the same way, through the interface `OrderItem`.

#### Scenario 4: Invalid input

The user enters a number that is not in the menu (`5`), a negative number (`-1`) and a text (`abc`), then chooses Espresso and enters a wrong size (`10`). The program reports an error each time and asks again.

```
Welcome to the Coffee Shop!

Menu:
1 - Espresso (2.5 $ for small)
2 - Latte (3.5 $ for small)
3 - Cappuccino (3.0 $ for small)
4 - Coffee Combo (any two drinks, 0.5 $ off)
0 - Finish order
Your choice: 5
Wrong choice, try again.
Your choice: -1
Wrong choice, try again.
Your choice: abc
Wrong choice, try again.
Your choice: 1
Size: 1 - Small, 2 - Medium (+0.5 $), 3 - Large (+1.0 $)
Your choice: 10
Wrong choice, try again.
Your choice: 
```

---

## Conclusion

This laboratory work demonstrated the practical use of three structural design patterns in the Java coffee shop project. The Decorator pattern was applied to the extras of a drink: the common interface `OrderItem`, the abstract decorator `DrinkExtra` and the concrete decorators `Syrup` and `Cinnamon` let the program add any number of extras in any order, and `Drink` no longer needs to know about them. Scenario 2 showed that the description follows the order of wrapping, while the price stays the same.

The Composite pattern was applied to the new Coffee Combo: `CoffeeCombo` implements the same interface as a single drink, so the order, the cashier and the receipt work with a combo and with a drink in the same way. Scenario 3 confirmed the discount of 0.50 $ and the mixed order. The combo can contain decorated drinks, which shows that Decorator and Composite work well together.

The Facade pattern was applied to the shop: `CoffeeShop` gives `Main` four simple operations for the menu and the checkout, and hides the `Cashier` and the other inner classes, which were not changed. The earlier creational patterns (Singleton, Prototype and Factory Method) still work inside the same program, so the three groups of patterns complement each other. Some limitations should be noted: the description of an item depends on the order of the extras, a combo has exactly two items (the pattern is used in a simplified form, without methods to add and remove children), and `Main` still creates the order, the decorators and the payment creator itself, so the facade covers only the menu and the checkout.

The work also showed that structural patterns make a program easier to extend: a new extra, a new kind of order item or a change inside the shop is made in new or inner classes, without changing the code that uses them.

---

## Bibliography

1. Coffee shop system – source code of the project [online resource]. Available at: <https://github.com/Orm-Max/tmps_repository> (accessed: 2026).
2. Structural DPs – Slides File [online resource]. Technical University of Moldova, FCIM. Available at: <https://else.fcim.utm.md/mod/resource/view.php?id=16049> (accessed: 2026).
