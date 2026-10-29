package drink;
public class Cappuccino extends Drink {
    public Cappuccino() {
        super("Cappuccino", 3.00);
    }

    private Cappuccino(Cappuccino other) {
        super(other);
    }

    @Override
    public Drink copy() {
        return new Cappuccino(this);
    }
}
