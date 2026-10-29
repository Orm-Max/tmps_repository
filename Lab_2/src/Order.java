import java.util.ArrayList;
import java.util.List;

import drink.Drink;

public class Order {
    private List<Drink> drinks = new ArrayList<>();

    public void addDrink(Drink drink) {
        drinks.add(drink);
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public double getTotal() {
        double total = 0;
        for (Drink drink : drinks) {
            total = total + drink.getPrice();
        }
        return total;
    }
}
