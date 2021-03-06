package app.employee;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by ep on 2018-05-22.
 */
@Embedded
public class Comment {
    private String employeeID;
    private int timestamp;
    private String employerID;
    private String comment;

    public String getComment() {
        return comment;
    }
    private Comment() {}

    @Override
    public String toString() {
        return employeeID  + " " + timestamp  + " " + employerID  + " " + comment;
    }
}
