package model.transaction;

// The movie ticket price can be charged according certain type
public interface Priceable {
    double getPrice();
    static double getPrice(Priceable... priceables) {
        double sum = 0;
        for (Priceable priceable : priceables)
            sum += priceable.getPrice();
        return sum;
    }
}
