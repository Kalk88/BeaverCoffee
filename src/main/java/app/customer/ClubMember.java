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

    public ClubMember() {}

    @Override
    public String toString() {
        return first + " " + last + " " + SSN  + " " + homeAdress.toString()  + " " +
                occupation  + " " + cards.toString();
    }
}
