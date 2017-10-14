package model.transaction;

import java.io.Serializable;

public class Pricing implements Serializable {

    private double price;

    public Pricing(Priceable... priceables) {
        for(Priceable priceable : priceables) {
            this.price += priceable.getPrice();
        }
    }

    public double getPrice() {
        return price;
    }

}
