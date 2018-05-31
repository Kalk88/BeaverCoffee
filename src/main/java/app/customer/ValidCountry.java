package app.customer;

import org.mongodb.morphia.annotations.Embedded;

/**
 * This class models data about where a club member card is valid.
 */

@Embedded
public class ValidCountry {
    private String code;
    private String name;

    private ValidCountry() {}

    @Override
    public String toString() {
        return String.format("%s, %s", code, name);
    }
}
