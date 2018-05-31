package app.employee;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by ep on 2018-05-22.
 */
@Embedded
public class EmploymentHistory {
    private String position;
    private int startTimestamp;
    private int endTimestamp;
    private String type;

    public EmploymentHistory() {
    }

    @Override
    public String toString() {
        return position + " " + startTimestamp + " " + endTimestamp + " " + type;
    }
}