package drink;
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
