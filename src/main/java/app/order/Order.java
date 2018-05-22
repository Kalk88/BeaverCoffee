package app.order;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.List;

@Entity("orders")
@Indexes(
        @Index(value = "timestamp", fields = @Field("timestamp"))
)
public class Order {
    @Id
    private ObjectId _id;
    private String id;
    private int timestamp;
    private String discount;
    @Embedded
    private OrderCustomer customer;
    private String employeeID;
    private String storeID;
    @Embedded
    private List<OrderProduct> products;
    private Status status;

    private Order() {}

    @Override
    public String toString() {
        return id + " " + timestamp + " " + discount + " " + customer.toString() + " " + employeeID + " " + " " + storeID + " "
                + " " + products.toString() + " " + status;
    }

    public void setId(String id) {
        this.id = id;
    }
}
