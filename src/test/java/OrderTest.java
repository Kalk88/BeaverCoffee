import app.order.Order;
import app.order.OrderController;
import app.order.OrderDao;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import static org.junit.Assert.*;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class OrderTest {
    private Morphia morphia;
    private Datastore datastore;
    private MongoClient client;

    @Before
    public void setup() {
        morphia = new Morphia();
        client = new MongoClient();
        morphia.mapPackage("com.babayaga.beavercoffee.order");
        datastore = morphia.createDatastore(client, "beaverDB");
        datastore.getDB().dropDatabase();
        datastore.getDB().createCollection("orders", null);
        datastore.ensureIndexes();

        try (Stream<String> stream = Files.lines(Paths.get("src/test/java/test_data/orders.txt"))) {
            MongoCollection<Document> collection = client.getDatabase("beaverDB").getCollection("orders");
            stream.map(Document::parse)
                    .forEach(collection::insertOne);

            System.out.println(collection.count());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void print_orders() {
        final Query<Order> query = datastore.createQuery(Order.class);
        final List<Order> orders = query.asList();
        orders.forEach(System.out::println);
    }

    @Test
    public void get_correct_number_of_orders() {
        final Query<Order> query = datastore.createQuery(Order.class);
        final List<Order> orders = query.asList();
        assertEquals(4, orders.size());
    }

    @Test
    public void get_orders_from_specific_timeperiod() {
        final Query<Order> query = datastore.createQuery(Order.class);
        final List<Order> orders = query.filter("timestamp >", 1526633863).asList();
        assertEquals(3, orders.size());
    }

    @Test
    public void get_orders_from_specific_zip() {
        final Query<Order> query = datastore.createQuery(Order.class);
        final List<Order> orders = query.filter("customer.clubmember.homeAdress.zip", "911 1337").asList();
        assertEquals(2, orders.size());
    }

    @Test
    public void get_orders_from_specific_occupation() {
        final Query<Order> query = datastore.createQuery(Order.class);
        final List<Order> orders = query.filter("customer.clubmember.occupation", "Youtube Influencer").asList();
        assertEquals(4, orders.size());
    }

    // Orders served by an employee for any specified time period
    @Test
    public void get_orders_from_employee_during_specified_timeperiod() {
        final Query<Order> query = datastore.createQuery(Order.class);
        query.filter("employeeID", "12345678-3715-4f91-8b4f-c4f3342f5a53");
        query.filter("timestamp >", 1526633865);
        final List<Order> orders = query.asList();
        assertEquals(1, orders.size());
    }

    @Test
    public void get_all_orders_from_api() {
        final OrderDao dao = new OrderDao(datastore);
        final OrderController controller = new OrderController(dao);
        final List<Order> orders = controller.getAllOrders();
        orders.forEach(System.out::println);
    }
}
