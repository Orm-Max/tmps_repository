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
