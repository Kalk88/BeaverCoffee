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
    private List<Card> cards;

    private ClubMember() {}

    @Override
    public String toString() {
        return first + " " + last + " " + SSN  + " " + homeAdress.toString()  + " " +
                occupation  + " " + cards.toString();
    }

    public boolean invariant() {
        if ( id != null && first != null && last != null && SSN != null &&
                homeAdress != null && occupation != null && cards != null) return true;
        return false;
    }
}
