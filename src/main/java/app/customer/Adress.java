package app.customer;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Adress {
    private String street;
    private String zip;
    private String city;
    private Country country;

    public Adress() {}

    @Override
    public String toString() {
        return street  + " " + zip  + " " + city  + " " + country.toString();
    }
}
