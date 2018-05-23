import app.customer.Customer;
import app.customer.CustomerController;
import app.customer.CustomerDao;
import app.customer.CustomerException;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * These tests depends on a mongoDB instance running on localhost.
 *
 */
public class CustomerTest {
    private Morphia morphia;
    private Datastore dataStore;

    @Before
    public void setup() {
        morphia = new Morphia();
        final MongoClient client = new MongoClient();
        morphia.mapPackage("com.babayaga.beavercoffee.customer"); // Can be called several times with diffrent packages if needed.
        dataStore = morphia.createDatastore(client, "beaverDB");
        dataStore.getDB().dropDatabase();
        dataStore.ensureIndexes();
        dataStore.getDB().createCollection("customers", null);

        // Load db with Customers.
        // Remember to start your local mongo instance =)))
        try (Stream<String> stream = Files.lines(Paths.get("src/test/java/test_data/customers.txt"))) {
            MongoCollection<Document> collection = client.getDatabase("beaverDB").getCollection("customers");
            stream.map(Document::parse)
                    .forEach(collection::insertOne);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //querys
    @Test
    public void should_retirieve_a_list_of_customers() {
        final Query<Customer> query = dataStore.createQuery(Customer.class);
        final List<Customer> customers = query.asList();
        assertEquals(9, customers.size());
    }

    //DAO
    @Test
    public void dao_should_return_a_list_of_customers() {
        final CustomerDao dao = new CustomerDao(dataStore);
        final List<Customer> customers = dao.getAllCustomers();
        assertEquals(9, customers.size());
    }


    //Controller
    @Test
    public void controller_should_return_a_list_of_customers() {
        final CustomerDao dao = new CustomerDao(dataStore);
        final CustomerController controller = new CustomerController(dao);
        final List<Customer> customers = controller.getAllCustomers();
        customers.forEach(System.out::println);
        assertEquals(9, customers.size());
    }

    @Test
    public void controller_should_return_customer_with_given_id() {
        final CustomerDao dao = new CustomerDao(dataStore);
        final CustomerController controller = new CustomerController(dao);
        final String id = "91234567-3715-4f91-8b4f-c4f3342f5a83"; // Last US member in customers.txt
        try {
            final Customer customer = controller.getCustomer(id);
            assertEquals(id, customer.getId());
        } catch (CustomerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void controller_should_throw_exception_if_no_customer_exists() {
        final CustomerDao dao = new CustomerDao(dataStore);
        final CustomerController controller = new CustomerController(dao);
        assertThrows(CustomerException.class, () -> {
            final Customer customer = controller.getCustomer("66666666-6666-4f91-8b4f-c4f3342f5a83");
        });
    }

}
