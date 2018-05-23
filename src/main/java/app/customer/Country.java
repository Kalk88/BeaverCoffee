package app.customer;

import org.mongodb.morphia.annotations.Embedded;

/**
 * This class models locale information.
 */

@Embedded
public class Country {
    private String code;
    private String currency;
    private String language;
    private String name;

    private Country() {}

    @Override
    public String toString() {
        return code  + " " + currency  + " " + language  + " " + name;
    }
}
