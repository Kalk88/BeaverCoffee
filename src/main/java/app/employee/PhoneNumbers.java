package app.employee;

import org.mongodb.morphia.annotations.Embedded;

import java.util.List;

/**
 * Created by ep on 2018-05-22.
 */
@Embedded
public class PhoneNumbers {
    private List<String> mobile;
    private List<String> home;

    public PhoneNumbers(){}

    @Override
    public String toString() {
        return mobile.toString() + " " + home.toString();
    }
}
