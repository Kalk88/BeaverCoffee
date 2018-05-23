package app.customer;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Models a BeaverCoffee club member card.
 */

@Embedded
public class Card {
    private String numOfPurchases;
    private String barcode;
    @Embedded
    private ValidCountry validCountry;

    private Card() {}

    @Override
    public String toString() {
        return String.format("purchases %s, barcode %s, valid in %s", numOfPurchases, barcode, validCountry);
    }
}
