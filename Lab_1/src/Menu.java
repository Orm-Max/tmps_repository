import java.util.ArrayList;
import java.util.List;

import drink.Drink;
import drink.Cappuccino;
import drink.Espresso;
import drink.Latte;
import drink.Mocha;

public class Menu {
    private List<Drink> drinks = new ArrayList<>();

    public Menu() {
        drinks.add(new Espresso());
        drinks.add(new Latte());
        drinks.add(new Cappuccino());
        drinks.add(new Mocha());
    }

    public List<Drink> getDrinks() {
        return drinks;
    }
}
