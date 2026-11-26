# Lab 4: Behavioral Design Patterns

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
  - [1.1 Behavioral Design Patterns](#11-behavioral-design-patterns)
  - [1.2 Patterns Selected for This Work](#12-patterns-selected-for-this-work)
- [2. Program and Results](#2-program-and-results)
  - [2.1 Project Description](#21-project-description)
  - [2.2 Applied Behavioral Patterns](#22-applied-behavioral-patterns)
  - [2.3 Source Code](#23-source-code)
  - [2.4 Program Output](#24-program-output)
- [Conclusion](#conclusion)
- [Bibliography](#bibliography)

---

## Introduction

Creational patterns answer the question of how objects are created, and structural patterns answer the question of how objects are combined. Behavioral design patterns answer a third question: how objects communicate with each other and how responsibilities are distributed between them. They describe not only the objects and classes, but also the patterns of communication between them, so that the flow of control in a program stays clear and flexible.

This laboratory work applies three behavioral patterns (Command, Observer and Strategy) in the Java coffee shop application that was developed in Laboratory works No.1, No.2 and No.3. The creational patterns (Singleton, Prototype, Factory Method) and the structural patterns (Decorator, Composite, Facade) from the previous works are kept. In this work the user gets the possibility to undo the last addition to the order (Command), the program reports every change of the order (Observer), and the user can choose between the regular price and a 10% discount at checkout (Strategy). The program is deliberately simple, so that each pattern can be seen clearly in a few short classes.

---

## 1. Theoretical Background

### 1.1 Behavioral Design Patterns

Behavioral design patterns are concerned with algorithms and with the assignment of responsibilities between objects. They describe how objects cooperate to perform a task that no single object can perform alone, and they reduce the coupling between the objects that communicate. The Gang of Four describes eleven behavioral patterns, which are summarised in Table 1.1.

*Table 1.1 – Behavioral design patterns*

| Pattern | Main idea |
| --- | --- |
| **Chain of Responsibility** | Passes a request along a chain of handlers; each handler decides to process the request or to pass it on |
| **Command** | Turns a request into an object, so requests can be stored, queued, logged and undone |
| **Interpreter** | Defines a grammar of a simple language and an interpreter that uses it to interpret sentences |
| **Iterator** | Gives sequential access to the elements of a collection without exposing its inner structure |
| **Mediator** | Defines an object that controls how a set of other objects interact, so they do not refer to each other directly |
| **Memento** | Saves the inner state of an object without breaking encapsulation, so the object can be restored later |
| **Observer** | Defines a one-to-many dependency: when one object changes its state, all its dependents are notified automatically |
| **State** | Lets an object change its behavior when its inner state changes |
| **Strategy** | Defines a family of interchangeable algorithms, puts each one into a separate class and lets the client choose which one to use |
| **Template Method** | Defines the skeleton of an algorithm in a base class and lets subclasses redefine some of its steps |
| **Visitor** | Adds new operations to the classes of an object structure without changing these classes |

### 1.2 Patterns Selected for This Work

Three patterns are demonstrated in this project:

- **Command** in the package `command`: adding an item to the order is represented by the object `AddItemCommand`, and the class `OrderHistory` stores the executed commands, so the last addition (a drink or a combo) can be undone.
- **Observer** in the class `Order` and the interface `OrderListener`: after an item is added or removed, the order notifies its listeners, which print the changed item and the state of the cart (the number of items and the subtotal).
- **Strategy** in the package `discount`: the interface `DiscountStrategy` has two implementations, the regular price and the loyalty discount of 10%. The cashier and the receipt use the same total, which is calculated by the order with the chosen strategy.

The other eight behavioral patterns are described in the theory above but are not demonstrated in this work.

---

## 2. Program and Results

### 2.1 Project Description

The application is a console program. The user sees a numbered menu with three drinks, a "Coffee Combo" item and an "Undo last addition" item. After the user enters `0` to finish the order, the program asks for a price option (regular price or loyalty discount of 10%) and a payment method (cash or card), and prints a receipt. Invalid input is handled by repeating the question.

The project is organised in the packages `command`, `discount`, `drink`, `drink.extra`, `order`, `payment` and `shop`, and consists of the classes listed in Table 2.1. The classes from Laboratory works No.1–3 are not listed again, except those that were changed.

*Table 2.1 – New and changed classes of the coffee shop project*

| Class / interface | Responsibility | Pattern |
| --- | --- | --- |
| `OrderCommand` | Interface of a command with two methods, `execute()` and `undo()` | Command |
| `AddItemCommand` | Concrete command: adds an item to the order, and removes it on undo | Command |
| `OrderHistory` | Invoker: executes commands and keeps them in a stack to undo them | Command |
| `Order` | Receiver of the commands, subject of the observers and context of the strategy (changed) | Command, Observer, Strategy |
| `OrderListener` | Interface of an observer: `itemChanged(order, item, added)` | Observer |
| `ItemChangeMessage` | Concrete observer: prints the changed item with "added." or "removed." | Observer |
| `CartSummary` | Concrete observer: prints the number of items and the subtotal | Observer |
| `DiscountStrategy` | Interface of a strategy: `calculateTotal(subtotal)` | Strategy |
| `RegularPrice` | Strategy: the total is equal to the subtotal | Strategy |
| `LoyaltyDiscount` | Strategy: the total is 90% of the subtotal, rounded to cents | Strategy |
| `ReceiptPrinter` | Prints the receipt; shows the subtotal and the discount if there is one (changed) | – (user of the total) |
| `Cashier` | Takes the payment for `order.getTotal()` (unchanged) | – (user of the total) |
| `Main` | Console input and output; creates commands, listeners and strategies (changed) | – (client) |

The class structure of the three patterns is shown in three UML class diagrams.

![Figure 2.1 – Class diagram of Command](command.png)

*Figure 2.1 – Class diagram of the Command pattern*

Figure 2.1 shows the part of the system that uses the **Command** pattern. The interface `OrderCommand` declares `execute()` and `undo()`. It is implemented by the concrete command `AddItemCommand`, which keeps the receiver (`Order`) and the item that must be added. The invoker `OrderHistory` aggregates the commands in a stack (`Deque`) and offers the methods `execute(command)` and `undo()`. The client `Main` creates the command (dashed arrow «create») and gives it to the history. The note at `AddItemCommand` shows that `execute()` calls `order.addItem(item)` and `undo()` calls `order.removeLastItem()`.

![Figure 2.2 – Class diagram of Observer](observer.png)

*Figure 2.2 – Class diagram of the Observer pattern*

Figure 2.2 shows the part of the system that uses the **Observer** pattern. `Order` is the subject: it keeps a list of listeners (0..\*) and has the methods `subscribe()` and `unsubscribe()`. The private method `notifyListeners()` calls `itemChanged()` of every listener. The interface `OrderListener` is implemented by two concrete observers: `ItemChangeMessage` prints the item with the word "added." or "removed.", and `CartSummary` prints the number of items and the subtotal of the order. Both observers receive the order as a parameter and read its state (dashed arrows «use»). The note at the bottom of the diagram shows how `Main` subscribes the observers.

![Figure 2.3 – Class diagram of Strategy](strategy.png)

*Figure 2.3 – Class diagram of the Strategy pattern*

Figure 2.3 shows the part of the system that uses the **Strategy** pattern. `Order` is the context: it holds a `DiscountStrategy` (the aggregation `discount`) and in `getTotal()` calculates the total with it. The interface `DiscountStrategy` is implemented by `RegularPrice` and `LoyaltyDiscount`. The classes `Cashier` and `ReceiptPrinter` depend only on `Order` and call `getTotal()` (the receipt also calls `getSubtotal()`), so both use the same total, whichever strategy is chosen.

### 2.2 Applied Behavioral Patterns

#### Command – package `command`

**Problem.** In Laboratory work No.3 the order was changed by a direct call: `Main` called `order.addItem(item)` and the item stayed in the order. If the user made a mistake (for example, added the wrong combo), there was no way to cancel it, and the program did not remember what was done. Adding an "undo" directly in `Main` would require storing the last actions there and knowing how to reverse each of them.

**Solution.** Every action on the order becomes an object with two methods, `execute()` and `undo()`. `AddItemCommand` stores the order and the item, adds the item on `execute()` and removes the last item on `undo()`. The class `OrderHistory` executes a command and puts it on a stack; on `undo()` it takes the last command from the stack, calls its `undo()` and removes it. If the stack is empty, `undo()` returns `false`. Because a single drink, a decorated drink and a combo are all `OrderItem` objects, the same command works for all of them.

```java
public interface OrderCommand {
    void execute();
    void undo();
}

public class AddItemCommand implements OrderCommand {
    private final Order order;
    private final OrderItem item;

    public AddItemCommand(Order order, OrderItem item) {
        this.order = order;
        this.item = item;
    }

    @Override
    public void execute() {
        order.addItem(item);
    }

    @Override
    public void undo() {
        order.removeLastItem();
    }
}

public class OrderHistory {
    private final Deque<OrderCommand> commands = new ArrayDeque<>();

    public void execute(OrderCommand command) {
        command.execute();
        commands.push(command);
    }

    public boolean undo() {
        if (commands.isEmpty()) {
            return false;
        }

        commands.peek().undo();
        commands.pop();
        return true;
    }
}
```

In `Main`, the addition of an item and the undo are done through the history:

```java
if (choice == undoNumber) {
    if (!history.undo()) {
        System.out.println("Nothing to undo.");
    }
    continue;
}
...
history.execute(new AddItemCommand(order, item));
```

**Result.** The last addition (a drink or a combo) can be undone, and the user can undo several additions one after another, until the history is empty. `Order` does not know anything about the history, and `Main` does not need to know how an action is reversed. A new action (for example, removing a chosen item) is a new class that implements `OrderCommand`; `OrderHistory` does not change.

#### Observer – class `Order` and interface `OrderListener`

**Problem.** In the previous version `Main` itself printed the text "... added." after the addition. With the undo, the program must also report a removal and show the state of the cart, and this would mean copying the output code to every place where the order changes. In addition, every new kind of reaction (for example, a log or a sound) would require changes in `Main` or in `Order`.

**Solution.** `Order` becomes the subject. It keeps a list of listeners and has the methods `subscribe()` and `unsubscribe()`. After every change (`addItem()` and `removeLastItem()`) it calls the private method `notifyListeners()`, which calls `itemChanged(order, item, added)` of every listener; the parameter `added` shows whether the item was added or removed. The method iterates over a copy of the list, so a listener can safely unsubscribe during the notification. The two concrete observers are independent classes: `ItemChangeMessage` prints the changed item, and `CartSummary` prints the number of items and the subtotal.

```java
public interface OrderListener {
    void itemChanged(Order order, OrderItem item, boolean added);
}

public class Order {
    private final List<OrderItem> items = new ArrayList<>();
    private final List<OrderListener> listeners = new ArrayList<>();
    ...
    public void addItem(OrderItem item) {
        items.add(item);
        notifyListeners(item, true);
    }

    public void removeLastItem() {
        OrderItem item = items.remove(items.size() - 1);
        notifyListeners(item, false);
    }

    public void subscribe(OrderListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(OrderListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(OrderItem item, boolean added) {
        for (OrderListener listener : new ArrayList<>(listeners)) {
            listener.itemChanged(this, item, added);
        }
    }
}

public class ItemChangeMessage implements OrderListener {
    @Override
    public void itemChanged(Order order, OrderItem item, boolean added) {
        String action = added ? " added." : " removed.";
        System.out.println(item.getDescription() + action);
    }
}

public class CartSummary implements OrderListener {
    @Override
    public void itemChanged(Order order, OrderItem item, boolean added) {
        System.out.printf(Locale.US, "Cart: %d item(s), %.2f $%n",
                order.getItems().size(), order.getSubtotal());
    }
}
```

`Main` only subscribes the observers once:

```java
Order order = new Order();
order.subscribe(new ItemChangeMessage());
order.subscribe(new CartSummary());
```

**Result.** After every addition and every undo, the program prints the changed item and the state of the cart, and neither `Main` nor the command contains this code. `Order` does not know the concrete observers; a new reaction is a new class that implements `OrderListener` and is subscribed in one line.

#### Strategy – package `discount`

**Problem.** The shop wants to offer a loyalty discount of 10% besides the regular price. If the discount were calculated by `if / else` in `Order`, in `Cashier` and in `ReceiptPrinter`, these classes could calculate different totals, and every new kind of discount would require changes in all of them.

**Solution.** The way of calculating the total is moved into the interface `DiscountStrategy` with one method, `calculateTotal(subtotal)`. `RegularPrice` returns the subtotal without changes, and `LoyaltyDiscount` returns 90% of the subtotal, rounded to cents. `Order` keeps the chosen strategy (by default it is `RegularPrice`) and calculates the total with it. `Cashier` and `ReceiptPrinter` do not know about discounts: they just ask the order for the total.

```java
public interface DiscountStrategy {
    double calculateTotal(double subtotal);
}

public class RegularPrice implements DiscountStrategy {
    @Override
    public double calculateTotal(double subtotal) {
        return subtotal;
    }
}

public class LoyaltyDiscount implements DiscountStrategy {
    @Override
    public double calculateTotal(double subtotal) {
        return Math.round(subtotal * 0.90 * 100) / 100.0;
    }
}

public class Order {
    ...
    private DiscountStrategy discount = new RegularPrice();

    public double getTotal() {
        return discount.calculateTotal(getSubtotal());
    }

    public void setDiscount(DiscountStrategy discount) {
        this.discount = discount;
    }
}
```

`Main` asks the user and sets the strategy before the checkout; the `Cashier` and the `ReceiptPrinter` use the same total:

```java
order.setDiscount(chooseDiscount(scanner));
shop.checkout(order, choosePayment(scanner));
```

```java
public void checkout(Order order) {                     // Cashier
    paymentMethod.pay(order.getTotal());
    printer.print(order);
}

// ReceiptPrinter
double subtotal = order.getSubtotal();
double total = order.getTotal();
if (total < subtotal) {
    System.out.printf(Locale.US, "SUBTOTAL: %.2f $%n", subtotal);
    System.out.printf(Locale.US, "DISCOUNT: %.2f $%n", subtotal - total);
}
System.out.printf(Locale.US, "TOTAL: %.2f $%n", total);
```

**Result.** The amount that is paid and the total on the receipt always come from the same method, `order.getTotal()`, so they cannot differ (see Scenario 4 in section 2.4). A new kind of price (for example, a happy-hour discount) is a new class that implements `DiscountStrategy`; `Order`, `Cashier` and `ReceiptPrinter` are not changed.

*Table 2.2 – What each pattern gave to the project*

| Pattern | Before | After |
| --- | --- | --- |
| Command | `Main` called `order.addItem()` directly; an addition could not be cancelled | An addition is an object; `OrderHistory` can undo the last additions, for a drink and for a combo in the same way |
| Observer | `Main` printed the message about the addition itself | `Order` notifies its listeners; the messages and the cart state are printed by separate classes after every addition and undo |
| Strategy | The total was the plain sum of the items | The total is calculated by a chosen strategy; `Cashier` and `ReceiptPrinter` use the same total |

### 2.3 Source Code

The complete source code of the project is available in the GitHub repository [[1]](#bibliography). The most important parts are shown in section 2.2. The way in which `Main` connects the three patterns is shown below:

```java
CoffeeShop shop = new CoffeeShop();
Order order = new Order();
order.subscribe(new ItemChangeMessage());          // Observer
order.subscribe(new CartSummary());                // Observer
OrderHistory history = new OrderHistory();         // Command
...
history.execute(new AddItemCommand(order, item));  // Command (Observer is notified inside)
...
order.setDiscount(chooseDiscount(scanner));        // Strategy
shop.checkout(order, choosePayment(scanner));      // Facade, Factory Method
```

The facade `CoffeeShop` and the class `Cashier` from Laboratory work No.3 are not changed.

### 2.4 Program Output

All outputs in this section are the real console output of the program; the repeated menu is shortened with `...` to save space. Scenarios 1–3 are three parts of one program run.

#### Scenario 1: Command and Observer – undo of a single drink

The user adds a Medium Espresso with Syrup and then cancels it with "Undo last addition". After each action the observers print the changed item and the state of the cart.

```
Menu:
1 - Espresso (2.5 $ for small)
2 - Latte (3.5 $ for small)
3 - Cappuccino (3.0 $ for small)
4 - Coffee Combo (any two drinks, 0.5 $ off)
5 - Undo last addition
0 - Finish order
Your choice: 1
Size: 1 - Small, 2 - Medium (+0.5 $), 3 - Large (+1.0 $)
Your choice: 2
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 1
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 0
Espresso (MEDIUM) + Syrup added.
Cart: 1 item(s), 3.50 $

Menu:
...
Your choice: 5
Espresso (MEDIUM) + Syrup removed.
Cart: 0 item(s), 0.00 $
```

The price of the drink is 2.5 + 0.5 (size) + 0.5 (Syrup) = 3.5 $, and after the undo the cart is empty again.

#### Scenario 2: Command – undo of a combo and an empty history

The user adds a Coffee Combo of two decorated drinks and cancels it. Then the user chooses "Undo last addition" once more, when there is nothing to undo.

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
Your choice: 1
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 2
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 0
Choose the second drink for the combo:
1 - Espresso (2.5 $ for small)
2 - Latte (3.5 $ for small)
3 - Cappuccino (3.0 $ for small)
Your choice: 3
Size: 1 - Small, 2 - Medium (+0.5 $), 3 - Large (+1.0 $)
Your choice: 3
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 1
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 0
Coffee Combo [Espresso (SMALL) + Syrup + Cinnamon, Cappuccino (LARGE) + Syrup] (discount 0.5 $) added.
Cart: 1 item(s), 7.50 $

Menu:
...
Your choice: 5
Coffee Combo [Espresso (SMALL) + Syrup + Cinnamon, Cappuccino (LARGE) + Syrup] (discount 0.5 $) removed.
Cart: 0 item(s), 0.00 $

Menu:
...
Your choice: 5
Nothing to undo.
```

The calculation is checked in Table 2.3.

*Table 2.3 – Price calculation for Scenario 2*

| Item | Calculation | Price |
| --- | --- | --- |
| Espresso (SMALL) + Syrup + Cinnamon | 2.5 + 0.0 (size) + 0.5 (Syrup) + 0.5 (Cinnamon) | 3.5 $ |
| Cappuccino (LARGE) + Syrup | 3.0 + 1.0 (size) + 0.5 (Syrup) | 4.5 $ |
| **Coffee Combo** | 3.5 + 4.5 − 0.5 (discount) | **7.5 $** |

The same command and the same history undo a combo as easily as a single drink, and when the history is empty the program answers `Nothing to undo.`

#### Scenario 3: Strategy – regular price

The run continues: the user adds a Small Espresso and a Medium Latte with Syrup, finishes the order, chooses the regular price and pays in cash.

```
Menu:
...
Your choice: 1
Size: 1 - Small, 2 - Medium (+0.5 $), 3 - Large (+1.0 $)
Your choice: 1
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 0
Espresso (SMALL) added.
Cart: 1 item(s), 2.50 $

Menu:
...
Your choice: 2
Size: 1 - Small, 2 - Medium (+0.5 $), 3 - Large (+1.0 $)
Your choice: 2
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 1
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 0
Latte (MEDIUM) + Syrup added.
Cart: 2 item(s), 7.00 $

Menu:
...
Your choice: 0

Choose a price option:
1 - Regular price
2 - Loyalty discount (10%)
Your choice: 1

How do you want to pay?
1 - Cash
2 - Card
Your choice: 1
Paid 7.0 $ in cash.
===== COFFEE SHOP RECEIPT =====
Espresso (SMALL) - 2.5 $
Latte (MEDIUM) + Syrup - 4.5 $
-------------------------------
TOTAL: 7.00 $
===============================
```

The total is 2.5 + (3.5 + 0.5 + 0.5) = 7.0 $. With the regular price the receipt has no lines for the subtotal and the discount.

#### Scenario 4: Strategy – loyalty discount of 10%

The user orders a Medium Cappuccino with Syrup (3.0 + 0.5 + 0.5 = 4.0 $), chooses the loyalty discount and pays in cash. The expected total is 4.0 × 0.9 = 3.6.

```
Welcome to the Coffee Shop!

Menu:
...
Your choice: 3
Size: 1 - Small, 2 - Medium (+0.5 $), 3 - Large (+1.0 $)
Your choice: 2
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 1
Extras (+0.5 $ each): 1 - Syrup, 2 - Cinnamon, 0 - Done
Your choice: 0
Cappuccino (MEDIUM) + Syrup added.
Cart: 1 item(s), 4.00 $

Menu:
...
Your choice: 0

Choose a price option:
1 - Regular price
2 - Loyalty discount (10%)
Your choice: 2

How do you want to pay?
1 - Cash
2 - Card
Your choice: 1
Paid 3.6 $ in cash.
===== COFFEE SHOP RECEIPT =====
Cappuccino (MEDIUM) + Syrup - 4.0 $
-------------------------------
SUBTOTAL: 4.00 $
DISCOUNT: 0.40 $
TOTAL: 3.60 $
===============================
```

The amount that was paid (3.6 $) is equal to the total on the receipt (3.60 $), because both come from `order.getTotal()`, and the receipt additionally shows the subtotal and the discount.

---

## Conclusion

This laboratory work demonstrated the practical use of three behavioral design patterns in the Java coffee shop project. The Command pattern turned the addition of an item into the object `AddItemCommand`, and the invoker `OrderHistory` stores the executed commands in a stack, so the user can undo the last additions one after another. Scenarios 1 and 2 showed that the same command works for a single drink and for a combo, and that an empty history is handled correctly.

The Observer pattern separated the reaction to a change of the order from the order itself: `Order` notifies its listeners after every addition and removal, and two independent classes print the changed item and the state of the cart. The Strategy pattern moved the calculation of the total into replaceable classes: the regular price and the loyalty discount of 10% are chosen at checkout, and `Cashier` and `ReceiptPrinter` use one total from `order.getTotal()`, so the paid amount and the receipt cannot differ (Scenario 4).

All patterns from the previous works still work inside the same program: the commands receive items that are built by the Decorator and the Composite, the strategy is applied before the checkout through the Facade, and the payment is created by the Factory Method. Some limitations should be noted: the only command is the addition of an item, and the undo cancels only the last additions in the reverse order; the observers write directly to the console and are called synchronously; the discount is chosen once, at checkout, and applies to the whole order.

The work also showed that behavioral patterns make the responsibilities of the classes clearer: a new action, a new reaction to a change or a new kind of price is added as a new class, while the code that already works stays the same.

---

## Bibliography

1. Coffee shop system – source code of the project [online resource]. Available at: <https://github.com/Orm-Max/tmps_repository> (accessed: 2026).
2. Behavioral DPs – Slides File [online resource]. Technical University of Moldova, FCIM. Available at: https://else.fcim.utm.md/course/section.php?id=1862 (accessed: 2026).
