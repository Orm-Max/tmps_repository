package drink;
public class Espresso extends Drink {
    public Espresso() {
        super("Espresso", 2.50);
    }

    private Espresso(Espresso other) {
        super(other);
    }

    @Override
    public Drink copy() {
        return new Espresso(this);
    }
}
