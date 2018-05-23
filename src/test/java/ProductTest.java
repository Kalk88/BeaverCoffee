import app.product.Product;
import app.product.ProductController;
import app.product.ProductDao;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


/**
 * These tests depends on a mongoDB running
 */
public class ProductTest {
    private Morphia morphia;
    private Datastore dataStore;

    @Before
    public void setup() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "WARN");
        morphia = new Morphia();
        final MongoClient client = new MongoClient();
        morphia.mapPackage("com.babayaga.beavercoffee.product"); // Can be called several times with diffrent packages if needed
        dataStore = morphia.createDatastore(client, "beaverDB");
        dataStore.getDB().dropDatabase();
        dataStore.ensureIndexes();
        dataStore.getDB().createCollection("products", null);

        // Load db with products
        // Remember to start your local mongo instance =)))
        try (Stream<String> stream = Files.lines(Paths.get("src/test/java/test_data/products_eng.txt"))) {
            MongoCollection<Document> collection = client.getDatabase("beaverDB").getCollection("products");
            stream.map(Document::parse)
                    .forEach(collection::insertOne);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void db_should_retrieve_a_list_of_products() {
        final Query<Product> query = dataStore.createQuery(Product.class);
        final List<Product> products = query.asList();
        assertEquals(14, products.size()); //expected then acutal
    }

    @Test
    public void dao_should_return_list_of_products() {
        final ProductDao dao = new ProductDao(dataStore);
        final List<Product> products =  dao.getAllProducts();
        assertEquals(14, products.size());
    }


    @Test
    public void controller_should_return_a_list_of_products() {
        final ProductDao dao = new ProductDao(dataStore);
        final ProductController controller = new ProductController(dao);
        final List<Product> products =  controller.getAllProducts();
        assertEquals(14, products.size());
    }
}
