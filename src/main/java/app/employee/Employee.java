package app.employee;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.List;

@Entity("employees")
@Indexes(
        @Index(value = "id", fields = @Field("id"))
)
public class Employee {
    @Id
    private ObjectId _id;
    private String id;
    @Embedded
    private Details details;
    @Embedded
    private List<Comment> comments;

    public String getId() {
        return id;
    }

    private Employee() {}

    private Employee(Employee employee, ObjectId _id) {
        this._id = _id;
        this.id = employee.id;
        this.details = employee.details;
        this.comments = employee.comments;
    }

    public Employee overwriteEmployee(Employee employee) {
        return new Employee(employee, this._id);
    }


    public void setId(String id) {
        this.id = id;
    }

    public ObjectId get_id() {
        return _id;
    }

    @Override
    public String toString() {
        return id + " " + details.toString()  + " " + comments.toString();
    }
}
