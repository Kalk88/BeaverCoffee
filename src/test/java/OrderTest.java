import app.order.Order;
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
    public void get_correct_number_of_orders() {
        final Query<Order> query = datastore.createQuery(Order.class);
        final List<Order> orders = query.asList();
        assertEquals(4, orders.size());
    }

    @Test
    public void get_orders_from_specific_timeperiod() {
        final Query<Order> query = datastore.createQuery(Order.class);
        final List<Order> orders = query.filter("timestamp >", 1526633863).asList();
        assertEquals(1, orders.size());
    }
}
