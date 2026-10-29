package drink;

import java.util.ArrayList;
import java.util.List;

public abstract class Drink {
    private static final double EXTRA_PRICE = 0.50; 

    private String name;
    private double price;
    private Size size = Size.SMALL;
    private List<String> extras = new ArrayList<>();

    public Drink(String name, double price) {
        this.name = name;
        this.price = price;
    }

    protected Drink(Drink other) {
        this.name = other.name;
        this.price = other.price;
        this.size = other.size;
        this.extras = new ArrayList<>(other.extras);
    }

    public abstract Drink copy();

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price + size.getExtraPrice() + extras.size() * EXTRA_PRICE;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void addExtra(String extra) {
        extras.add(extra);
    }

    public List<String> getExtras() {
        return extras;
    }

    public String getDescription() {
        String text = name + " (" + size + ")";
        for (String extra : extras) {
            text = text + " + " + extra;
        }
        return text;
    }
}
