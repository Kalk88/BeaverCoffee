package app.order;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class OrderDao {
    private Datastore datastore;

    public OrderDao(Datastore datastore) {
        this.datastore = datastore;
    }

    public List<Order> getAllOrders() {
        final Query<Order> query = datastore.createQuery(Order.class);
        final List<Order> orders = query.asList();
        return orders;
    }
}
