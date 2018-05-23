package app.customer;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class CustomerDao {
    private Datastore datastore;

    public CustomerDao(Datastore datastore) {
        this.datastore = datastore;
    }


    /**
     * Returns a list of all customers in the database.
     * @return List<Customer>
     */
    public List<Customer> getAllCustomers() {
        final Query<Customer> query = datastore.createQuery(Customer.class);
        return query.asList();
    }

    /**
     * Returns a Customer with the given id if it exists or throws a IndexOutOfBoundsException.
     * @param id of Customer
     * @return Customer
     */
    public Customer getCustomer(String id) {
        final Query<Customer> query = datastore.createQuery(Customer.class);
        final List<Customer> customer = query.field("id").equal(id).asList();
        return customer.get(0);
    }
}
