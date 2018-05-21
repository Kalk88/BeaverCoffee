package app.order;

import app.customer.ClubMember;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

@Entity("customers")
@Indexes(
        @Index(value = "id", fields = @Field("id"))
)
public class OrderCustomer {
    @Id
    private ObjectId _id;
    private String id;
    @Embedded
    private ClubMember clubmember;

    public OrderCustomer() {}

    @Override
    public String toString() {
        return id  + " " + clubmember.toString();
    }
}
