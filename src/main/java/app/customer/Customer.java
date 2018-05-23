package app.customer;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

/**
 * This class models a BeaverCoffee customer.
 */

@Entity("customers")
@Indexes(
        @Index(value = "id", fields = @Field("id"))
)
public class Customer {
    @Id
    private ObjectId _id;
    private String id;
    @Embedded
    private ClubMember clubmember;

    private Customer() {}

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id  + " " + clubmember.toString();
    }


    public boolean invariant() {
        if(id != null && clubmember != null && clubmember.invariant()) return true;
        else if(id != null && clubmember == null) return true;
        else return false;
    }
}
