package app.store;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Stock {

    private String productId;
    private double quantity;

    private Stock(){

    }

    public String getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        return productId + ": " + quantity;
    }
}
