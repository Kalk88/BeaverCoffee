package app.employee;

import app.customer.Adress;
import org.mongodb.morphia.annotations.Embedded;

import java.util.List;

/**
 * Created by ep on 2018-05-22.
 */
@Embedded
public class Details {
    private String first;
    private String last;
    private String SSN;
    private List<Adress> homeAdress;
    private PhoneNumbers phoneNumbers;
    private List<EmploymentHistory> employmentHistory;

    private Details() {}

    @Override
    public String toString() {
        return first + " " + last + " " + SSN  + " " + homeAdress.toString()  + " " + phoneNumbers.toString() + " " + employmentHistory.toString();
    }
}
