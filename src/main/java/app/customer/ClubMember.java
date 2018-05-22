package app.customer;

import org.mongodb.morphia.annotations.Embedded;

import java.util.List;

@Embedded
public class ClubMember {
    private String id;
    private String first;
    private String last;
    private String SSN;
    private List<Adress> homeAdress;
    private String occupation;
    private Country validCountry;

    public ClubMember() {}

    @Override
    public String toString() {
        return first + " " + last + " " + SSN  + " " + homeAdress.toString()  + " " +
                occupation  + " " + validCountry.toString();
    }
}
