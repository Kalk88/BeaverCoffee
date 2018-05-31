package app.order;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

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

    private Order(Order order, ObjectId _id) {
        this._id = _id;
        this.id = order.id;
        this.timestamp = order.timestamp;
        this.discount = order.discount;
        this.customer = order.customer;
        this.employeeID = order.employeeID;
        this.storeID = order.storeID;
        this.products = order.products;
        this.status = order.status;
    }

    public Order overwriteOrder(Order order) {
        return new Order(order, this._id);
    }

    @Override
    public String toString() {
        return String.format(
                "%s %d %s %s %s %s %s %s",
                id, timestamp, discount, customer.toString(),
                employeeID, storeID, products.toString(), status);
    }

    /**
     * Setter for ID.
     * @param id of order.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for ID.
     * @return String.
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for ObjectID.
     * @return String.
     */
    public ObjectId get_id() {
        return _id;
    }

    /**
     * Getter for Status of order.
     * @return Status
     */
    public Status getStatus() {
        return status;
    }
}
