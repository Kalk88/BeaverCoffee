package app.store;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;
import java.util.LinkedList;

@Indexes(
        @Index(value = "id", fields = @Field("id"))
)
@Entity("stores")
public class Store {
    @Id
    private ObjectId _id;
    private String id;
    private String branchID;
    @Embedded
    private Adress adress;
    private LinkedList<Stock> stock;
    private LinkedList<String> orders;

    public LinkedList<Stock> getStock() {
        return stock;
    }


    public LinkedList<String> getOrders() {
        return orders;
    }

    public void setStock(LinkedList<Stock> stock) {
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "id: " + id + ", branchID: " + branchID + ", adress: " + adress + ", stock: " + stock + ", ordersIDs: " +
        orders;
    }
}


