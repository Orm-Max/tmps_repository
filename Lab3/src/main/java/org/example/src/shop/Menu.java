package org.example.src.shop;

import java.util.Arrays;
import java.util.List;

import org.example.src.drink.Drink;
import org.example.src.drink.Cappuccino;
import org.example.src.drink.Espresso;
import org.example.src.drink.Latte;

public class Menu {
    private static volatile Menu instance;

    private final List<Drink> drinks;

    private Menu() {
        drinks = Arrays.asList(new Espresso(), new Latte(), new Cappuccino());
    }

    public static Menu getInstance() {
        if (instance == null) {                
            synchronized (Menu.class) { 
                if (instance == null) { 
                    instance = new Menu();
                }
            }
        }
        return instance;
    }

    int getDrinkCount() {
        return drinks.size();
    }

    Drink copyDrink(int index) {
        return drinks.get(index).copy();
    }

    String getDrinkLabel(int index) {
        Drink drink = drinks.get(index);
        return drink.getName() + " (" + drink.getPrice() + " $ for small)";
    }
}
