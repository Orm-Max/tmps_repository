# Lab 2: Creational Design Patterns

**MINISTRY OF EDUCATION, CULTURE AND RESEARCH**  
**OF THE REPUBLIC OF MOLDOVA**  

**Technical University of Moldova**  
**Faculty of Computers, Informatics and Microelectronics**  
**Department of Software Engineering and Automation**  


**MAXIM ORMANJI FAF-242**

Checked by: Reițman Petricia

Chișinău, 2026

---

## Table of Contents

- [Introduction](#introduction)
- [1. Theoretical Background](#1-theoretical-background)
  - [1.1 Creational Design Patterns](#11-creational-design-patterns)
  - [1.2 Patterns Selected for This Work](#12-patterns-selected-for-this-work)
- [2. Program and Results](#2-program-and-results)
  - [2.1 Project Description](#21-project-description)
  - [2.2 Applied Creational Patterns](#22-applied-creational-patterns)
  - [2.3 Source Code](#23-source-code)
  - [2.4 Program Output](#24-program-output)
- [Conclusion](#conclusion)
- [Bibliography](#bibliography)

---

## Introduction

Every program has to create its objects somewhere. The simplest way is the operator `new`, but a direct `new ConcreteClass(...)` ties the calling code to one concrete class: if the class changes, or a new variant appears, every place that calls `new` has to be found and edited. Creational design patterns, described by the "Gang of Four" in 1994, solve this problem. They separate the code that *uses* an object from the code that *creates* it.

This laboratory work applies three creational patterns (Singleton, Prototype and Factory Method) in a small Java application: the coffee shop ordering system built in Laboratory work No.1. The user chooses drinks from a menu, selects the size and extras, selects a payment method, and receives a receipt. The classes that were designed with the SOLID principles in the first laboratory work (`Order`, `Cashier`, `ReceiptPrinter`, `PaymentMethod`, `CashPayment`, `CardPayment`) are kept unchanged, and the patterns are added around them. The program is deliberately simple, so that each pattern can be seen clearly in a few short classes.

---

## 1. Theoretical Background

### 1.1 Creational Design Patterns

Creational design patterns are all about class instantiation. They hide how objects are created, so the rest of the program does not depend on concrete classes. This group is divided into two subgroups:

- **Class-Creation patterns** use inheritance: a subclass decides which class to instantiate.
- **Object-Creation patterns** use delegation: the creation of an object is handed over to another object.

The Gang of Four describes five creational patterns, which are summarised in Table 1.1.

*Table 1.1 – Creational design patterns*

| Pattern | Main idea | Group |
| --- | --- | --- |
| **Singleton** | A class that has only one instance at any time. It holds a static encapsulated instance, its constructor is private, and it must be thread safe | Object-Creation |
| **Prototype** | Initialised instances that are meant to be cloned, together with a collection that caches these prototype objects | Object-Creation |
| **Factory Method** | Creates an instance out of several related derived classes. It is usually used together with other creational patterns | Class-Creation |
| **Abstract Factory** | An interface for creating families of related objects without specifying the concrete classes. It defines a factory method for each type of product | Object-Creation |
| **Builder** | Separates the construction of an object from its representation, so the same construction process can create different representations | Object-Creation |

### 1.2 Patterns Selected for This Work

Three patterns are demonstrated in this project:

- **Singleton** in the class `Menu`: a coffee shop has one menu, so the program must have exactly one `Menu` object. The thread-safe variant with double-checked locking and `volatile` is used.
- **Prototype** in the class `Drink` and its subclasses: the drinks stored in the menu are prototypes. The customer never receives them directly; the program makes a copy of the chosen drink and configures only the copy (size and extras).
- **Factory Method** in the package `payment`: the abstract class `PaymentCreator` declares the factory method `createPayment()`, and its subclasses decide which `PaymentMethod` is created.

Abstract Factory and Builder are described in the theory above but are not demonstrated in this work.

---

## 2. Program and Results

### 2.1 Project Description

The application is a console program. The user sees a numbered menu, adds drinks one by one (choosing a size and optional extras for each), enters `0` to finish the order, chooses a payment method (cash or card), and receives a receipt. Invalid input is handled by repeating the question. The drink price is calculated as *base price + size price + 0.5 $ for each extra*. The sizes are Small (+0.0 $), Medium (+0.5 $) and Large (+1.0 $).

The project is organised in two packages, `drink` and `payment`, and consists of the classes listed in Table 2.1.

*Table 2.1 – Classes of the coffee shop project*

| Class / interface | Responsibility | Pattern |
| --- | --- | --- |
| `Menu` | Holds the only list of drinks sold by the shop; provides `getInstance()` | Singleton |
| `Drink` | Abstract base class: name, base price, size, extras; declares `copy()` and the copy constructor | Prototype |
| `Espresso`, `Latte`, `Cappuccino` | Concrete drinks; each one implements `copy()` through its own copy constructor | Prototype |
| `Size` | Enumeration of drink sizes (`SMALL`, `MEDIUM`, `LARGE`) with the extra price of each size | – |
| `PaymentCreator` | Abstract creator; declares the factory method `createPayment()` | Factory Method |
| `CashPaymentCreator`, `CardPaymentCreator` | Concrete creators; each one creates its own `PaymentMethod` | Factory Method |
| `PaymentMethod` | Interface with one method, `pay(double amount)` | – (unchanged) |
| `CashPayment`, `CardPayment` | Implementations of `PaymentMethod` | – (unchanged) |
| `Order` | Stores the chosen drinks and calculates the total | – (unchanged) |
| `ReceiptPrinter` | Prints the receipt for an order (now with size and extras) | – (small change) |
| `Cashier` | Performs the checkout: takes the payment and prints the receipt | – (unchanged) |
| `Main` | Console input and output; uses the three patterns | – |

The class structure of the system is shown in two UML class diagrams, one for the drink part and one for the payment part.

<img width="1098" height="827" alt="Figure 2.1 - Class diagram of the drink part" src="https://github.com/user-attachments/assets/77f59858-e665-4b26-904d-d100fd361f3d" />

*Figure 2.1 – Class diagram of the drink part: Singleton and Prototype*

Figure 2.1 shows the part of the system that uses the **Singleton** and **Prototype** patterns. The class `Menu` has a private constructor and a static `getInstance()` method (Singleton); it aggregates `Drink` objects (0..*), which are the prototypes of the shop. The abstract class `Drink` declares the abstract method `copy()` and has the enumeration `Size` as an association. The concrete drinks `Espresso`, `Latte` and `Cappuccino` inherit from `Drink`, and each of them implements `copy()` with `return new X(this)`. The class `Order` also aggregates `Drink` objects, which are copies of the prototypes.

<img width="1280" height="487" alt="Figure 2.2 - Class diagram of the payment part" src="https://github.com/user-attachments/assets/3a5d0031-1c8a-42dc-adc3-92efa3dac5b6" />

*Figure 2.2 – Class diagram of the payment part: Factory Method*

Figure 2.2 shows the part of the system that uses the **Factory Method** pattern. The abstract creator `PaymentCreator` declares the factory method `createPayment()`. Its subclasses `CashPaymentCreator` and `CardPaymentCreator` override it and create `CashPayment` and `CardPayment` (dashed arrows with the stereotype «create»). Both payment classes implement the interface `PaymentMethod`. The class `Cashier` depends only on the interface `PaymentMethod` and on `ReceiptPrinter`, so it does not know which concrete payment is used.

### 2.2 Applied Creational Patterns

#### Singleton – class `Menu`

**Problem.** In the first version, the constructor of `Menu` was public, so any class could write `new Menu()` and get its own separate menu with its own list of drinks. The coffee shop has only one menu, and the program should reflect this.

**Solution.** The constructor is made `private`, the only instance is stored in a `private static volatile` field, and the only way to get it is the static method `getInstance()`. The method uses double-checked locking: the first check (without a lock) is fast when the menu already exists, and the second check (inside `synchronized`) makes sure that two threads cannot create two menus at the same time. The keyword `volatile` prevents another thread from seeing a reference to a menu that is not yet fully constructed.

```java
public class Menu {
    private static volatile Menu instance;

    private List<Drink> drinks = new ArrayList<>();

    private Menu() {
        drinks.add(new Espresso());
        drinks.add(new Latte());
        drinks.add(new Cappuccino());
    }

    public static Menu getInstance() {
        if (instance == null) {                 // 1st check (no lock)
            synchronized (Menu.class) {
                if (instance == null) {         // 2nd check (with lock)
                    instance = new Menu();
                }
            }
        }
        return instance;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }
}
```

In `Main`, `new Menu()` is replaced with `Menu.getInstance()`.

**Result.** The program always has exactly one menu, and only `Menu` itself decides when it is created. An attempt to write `new Menu()` outside the class does not compile (`Menu() has private access in Menu`).

#### Prototype – class `Drink` and its subclasses

**Problem.** In Laboratory work No.1 a drink had only a name and a price and never changed, so copying it made no sense. When size and extras are added, a drink becomes a mutable object. If `Main` puts the object taken directly from the menu into the order, then two lattes in one order are the same object, and changing the size of one changes both, as well as the sample in the menu. Creating drinks with `new` in `Main` would require a chain of `if` statements that selects the class by the menu number and has to be updated for every new drink.

**Solution.** The drinks in the menu are treated as prototypes. The base class `Drink` declares the abstract method `copy()` and a protected copy constructor. Each concrete drink implements `copy()` through its own private copy constructor, so the copy has the same class as the original. The list of extras is copied into a **new** list; if the reference was copied instead, the original and the copy would share one list (a shallow copy), and an extra added to one drink would appear in the other.

```java
public abstract class Drink {
    private static final double EXTRA_PRICE = 0.50;

    private String name;
    private double price;                       // base price (small size)
    private Size size = Size.SMALL;
    private List<String> extras = new ArrayList<>();

    // Prototype: copy constructor
    protected Drink(Drink other) {
        this.name = other.name;
        this.price = other.price;
        this.size = other.size;
        this.extras = new ArrayList<>(other.extras);   // NEW list (deep copy)
    }

    // Prototype: every drink knows how to copy itself
    public abstract Drink copy();

    public double getPrice() {
        return price + size.getExtraPrice() + extras.size() * EXTRA_PRICE;
    }
    // getName(), setSize(), addExtra(), getDescription() ...
}

public class Latte extends Drink {
    public Latte() {
        super("Latte", 3.50);
    }

    private Latte(Latte other) {
        super(other);
    }

    @Override
    public Drink copy() {
        return new Latte(this);
    }
}
```

In `Main`, the customer receives a copy, and only the copy is configured:

```java
Drink chosen = drinks.get(choice - 1).copy();   // copy, not the sample from the menu
chosen.setSize(chooseSize(scanner));
addExtras(scanner, chosen);
order.addDrink(chosen);
```

**Result.** Every drink in the order is an independent object. `Main` does not need to know the concrete class of the drink and works only with the type `Drink`. The menu keeps its samples unchanged, so its prices are always the same.

#### Factory Method – package `payment`

**Problem.** In the first version, `Main` imported `CashPayment` and `CardPayment` and created them itself with `new`. `Main` therefore knew the concrete payment classes, and every new payment method required changes in the creation logic of `Main`.

**Solution.** The abstract class `PaymentCreator` declares the factory method `createPayment()`. Each subclass overrides it and returns the payment method it is responsible for. The creator of card payments receives the card number in its constructor, because it needs this data to create `CardPayment`.

```java
public abstract class PaymentCreator {
    public abstract PaymentMethod createPayment();   // factory method
}

public class CashPaymentCreator extends PaymentCreator {
    @Override
    public PaymentMethod createPayment() {
        return new CashPayment();
    }
}

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
```

`Main` chooses a creator and calls the factory method:

```java
PaymentCreator creator = null;
while (creator == null) {
    ...
    if (choice.equals("1")) {
        creator = new CashPaymentCreator();
    } else if (choice.equals("2")) {
        ...
        creator = new CardPaymentCreator(cardNumber);
    }
}
PaymentMethod payment = creator.createPayment();
Cashier cashier = new Cashier(payment, new ReceiptPrinter());
```

**Result.** `Main` no longer imports or creates `CashPayment` and `CardPayment`; it works only with `PaymentCreator` and `PaymentMethod`. `Cashier` is unchanged: it receives a ready `PaymentMethod` through its constructor and does not know how the object was created. Note that `Main` still contains an `if / else` that chooses *which creator* to use, because the choice depends on the user's input; the pattern removes the knowledge about *how* the payment is created, not the choice itself.

*Table 2.2 – What each pattern gave to the project*

| Pattern | Before | After |
| --- | --- | --- |
| Singleton | Any class could create its own `Menu` with `new Menu()` | Exactly one `Menu`; creation is controlled by the class itself |
| Prototype | The object from the menu could be put into the order and modified; `new` required a class choice in `Main` | The order receives independent copies; `Main` works only with `Drink` |
| Factory Method | `Main` created `CashPayment` and `CardPayment` directly | `Main` works with `PaymentCreator`; a new payment method needs new classes only |

### 2.3 Source Code

The complete source code of the project is available in the GitHub repository [[1]](#bibliography). Besides the fragments shown in section 2.2, the most important remaining parts are given below.

**`Size` (enumeration used by Prototype):**

```java
package drink;

public enum Size {
    SMALL(0.0),
    MEDIUM(0.5),
    LARGE(1.0);

    private double extraPrice;

    Size(double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public double getExtraPrice() {
        return extraPrice;
    }
}
```

**`Main` (fragments):**

```java
Menu menu = Menu.getInstance();                 // Singleton
List<Drink> drinks = menu.getDrinks();
Order order = new Order();

...
} else if (choice >= 1 && choice <= drinks.size()) {
    Drink chosen = drinks.get(choice - 1).copy();   // Prototype
    chosen.setSize(chooseSize(scanner));
    addExtras(scanner, chosen);
    order.addDrink(chosen);
}
...
PaymentMethod payment = creator.createPayment();    // Factory Method
Cashier cashier = new Cashier(payment, new ReceiptPrinter());
cashier.checkout(order);
```

**`Cashier` (unchanged from Laboratory work No.1):**

```java
public class Cashier {
    private PaymentMethod paymentMethod;
    private ReceiptPrinter printer;

    public Cashier(PaymentMethod paymentMethod, ReceiptPrinter printer) {
        this.paymentMethod = paymentMethod;
        this.printer = printer;
    }

    public void checkout(Order order) {
        paymentMethod.pay(order.getTotal());
        printer.print(order);
    }
}
```

### 2.4 Program Output

#### Scenario 1: Payment in Cash

The user orders one Espresso and one Latte (both Small, no extras) and pays in cash. The expected total is 2.5 + 3.5 = 6.0.

```
Welcome to the Coffee Shop!

Menu:
1 - Espresso (2.5 $ for small)
2 - Latte (3.5 $ for small)
3 - Cappuccino (3.0 $ for small)
0 - Finish order
Your choice: 1
Size: 1 - Small, 2 - Medium (+0.5 $), 3 - Large (+1.0 $)
Your choice: 1
Extra (press Enter to finish):
Espresso (SMALL) added.

...

How do you want to pay?
1 - Cash
2 - Card
Your choice: 1

Paid 6.0 $ in cash.
===== COFFEE SHOP RECEIPT =====
Espresso (SMALL) - 2.5 $
Latte (SMALL) - 3.5 $
-------------------------------
TOTAL: 6.0 $
===============================
```

#### Scenario 2: Payment by Card

The user orders two Cappuccinos and one Espresso (all Small) and pays by card. The expected total is 3.0 + 3.0 + 2.5 = 8.5.

```
...
How do you want to pay?
1 - Cash
2 - Card
Your choice: 2
Enter card number: 4444 5555 6666 7777

Paid 8.5 $ with card 4444 5555 6666 7777.
===== COFFEE SHOP RECEIPT =====
Cappuccino (SMALL) - 3.0 $
Cappuccino (SMALL) - 3.0 $
Espresso (SMALL) - 2.5 $
-------------------------------
TOTAL: 8.5 $
===============================
```

#### Scenario 3: Invalid Input

The user enters a number that is not in the menu (`9`) and a text instead of a number (`abc`). The program reports an error and shows the menu again.

```
Welcome to the Coffee Shop!

Menu:
1 - Espresso (2.5 $ for small)
2 - Latte (3.5 $ for small)
3 - Cappuccino (3.0 $ for small)
0 - Finish order
Your choice: 9
Wrong choice, try again.

Menu:
1 - Espresso (2.5 $ for small)
2 - Latte (3.5 $ for small)
3 - Cappuccino (3.0 $ for small)
0 - Finish order
Your choice: abc
Wrong choice, try again.

Menu:
1 - Espresso (2.5 $ for small)
2 - Latte (3.5 $ for small)
3 - Cappuccino (3.0 $ for small)
0 - Finish order
Your choice: 
```

#### Scenario 4: Prototype – independent copies with size and extras

The user orders a Large Latte with the extras Syrup and Cinnamon, then a Small Latte without extras, then a Medium Espresso, and pays by card. Both lattes were created from the same prototype in the menu, but they have different settings, and the menu keeps showing the original prices (2.5, 3.5 and 3.0).

```
Paid 12.0 $ with card 1234.
===== COFFEE SHOP RECEIPT =====
Latte (LARGE) + Syrup + Cinnamon - 5.5 $
Latte (SMALL) - 3.5 $
Espresso (MEDIUM) - 3.0 $
-------------------------------
TOTAL: 12.0 $
===============================
```

## Conclusion

This laboratory work demonstrated the practical use of three creational design patterns in a small Java project. The Singleton pattern was applied to the class `Menu`: its constructor is private, and the thread-safe method `getInstance()` with double-checked locking and `volatile` guarantees that the shop has exactly one menu.

The Prototype pattern was applied to the drinks. To make copying meaningful, the drinks were extended with a size and a list of extras. The menu now stores prototypes, and every drink in an order is a separate copy created by the method `copy()` through a copy constructor. The list of extras is copied into a new list, which avoids the common error of a shallow copy; Scenario 4 shows that two lattes created from the same prototype keep different sizes and extras, and the menu prices stay unchanged.

The Factory Method pattern was applied to the payment methods: the abstract class `PaymentCreator` and its two subclasses moved the creation of `CashPayment` and `CardPayment` out of `Main`. The classes written with the SOLID principles in the first laboratory work, in particular `Cashier` and `PaymentMethod`, were not changed, which shows that the patterns and the principles work together. One limitation should be noted: `Main` still chooses which creator to use, and the list of prototypes returned by `Menu` is not protected from accidental modification; in a larger project it would be made unmodifiable.

The work also showed that creational patterns pay off as a program grows: a new drink or a new payment method can be added through new classes, without changing the code that already works.

---

## Bibliography

1. Coffee shop system – source code of the project [online resource]. Available at: <https://github.com/Orm-Max/tmps_repository> (accessed: 2026).
2. Drumea V. *Creational Design Patterns* [presentation]. Software Design Techniques and Mechanisms, Technical University of Moldova, 2021.
3. Creational Design Patterns [video playlist, online resource]. YouTube. Available at: <https://www.youtube.com/watch?v=mE3qTp1TEbg&list=PLlsmxlJgn1HJpa28yHzkBmUY-Ty71ZUGc> (accessed: 2026).
