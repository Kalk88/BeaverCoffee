package app.employee;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Country {
    private String code;
    private String currency;
    private String language;
    private String name;

    public Country() {}

    @Override
    public String toString() {
        return code  + " " + currency  + " " + language  + " " + name;
    }
}
