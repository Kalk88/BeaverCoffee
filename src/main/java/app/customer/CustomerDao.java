package app.customer;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class CustomerDao {
    private Datastore datastore;

    public CustomerDao(Datastore datastore) {
        this.datastore = datastore;
    }

    public List<Customer> getAllCustomers() {
        final Query<Customer> query = datastore.createQuery(Customer.class);
        return query.asList();
    }

    public Customer getCustomer(String id) {
        final Query<Customer> query = datastore.createQuery(Customer.class);
        final List<Customer> customer = query.field("id").equal(id).asList();
        return customer.get(0);
    }
}
