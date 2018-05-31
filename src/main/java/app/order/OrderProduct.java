package app.order;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class OrderProduct {
    private String productID;
    private double quantity;

    public OrderProduct() {}

    @Override
    public String toString() {
        return String.format("%s %f", productID, quantity);
    }
}
