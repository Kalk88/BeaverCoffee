package app.order;

import app.util.Utils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;
import java.util.Map;

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

    public Order getOrderById(String id) {
        final Query<Order> query = datastore.createQuery(Order.class);
        final Order order = query.field("id").equal(id).get();
        return order;
    }

    public void insertOrder(Order order) {
        datastore.save(order);
    }

    public void deleteOrder(Order order) {
        datastore.delete(order);
    }

    public List<Order> getOrdersFromQueryParams(Map<String, String[]> params) {
        final Query<Order> query = datastore.createQuery(Order.class);

        params.forEach((k, v) -> {
            switch(k) {
                case "status": addStatusParams(query, v);
                break;
                case "from": addFromTimestampToQuery(query, v);
                break;
                case "to": addToTimestampToQuery(query, v);
                break;
                case "employeeID": addEmployeeIDs(query, v);
                break;
            }
        });
        final List<Order> orders = query.asList();
        return orders;
    }

    private Query<Order> addFromTimestampToQuery(Query<Order> query, String[] params) {
        for (String item : params) {
            query.filter("timestamp >=", Utils.getUnixTimestampFromDateString(item));
        }
        return query;
    }

    private Query<Order> addToTimestampToQuery(Query<Order> query, String[] params) {
        for (String item : params) {
            query.filter("timestamp <=", Utils.getUnixTimestampFromDateString(item));
        }
        return query;
    }

    private Query<Order> addStatusParams(Query<Order> query, String[] params) {
        for (String item : params) {
            query.filter("status", Status.valueOf(item));
        }
        return query;
    }

    private Query<Order> addEmployeeIDs(Query<Order> query, String[] params) {
        for (String item : params) {
            query.filter("employeeID", item);
        }
        return query;
    }
}
