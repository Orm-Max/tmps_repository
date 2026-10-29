import java.util.ArrayList;
import java.util.List;

import drink.Drink;
import drink.Cappuccino;
import drink.Espresso;
import drink.Latte;

public class Menu {
    private static volatile Menu instance;

    private List<Drink> drinks = new ArrayList<>();

    private Menu() {
        drinks.add(new Espresso());
        drinks.add(new Latte());
        drinks.add(new Cappuccino());
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

    public List<Drink> getDrinks() {
        return drinks;
    }
}
