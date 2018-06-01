import app.order.Order;
import app.store.*;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import test_data.dummy_data.OrderDummy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class StoreTest {

    private Morphia morphia;
    private Datastore dataStore;

    @Before
    public void setup() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "WARN");
        morphia = new Morphia();
        final MongoClient client = new MongoClient();
        morphia.mapPackage("com.babayaga.beavercoffee.store"); // Can be called several times with diffrent packages if needed
        morphia.mapPackage("com.babayaga.beavercoffee.order"); // Can be called several times with diffrent packages if needed
        dataStore = morphia.createDatastore(client, "beaverDB");
        dataStore.getDB().dropDatabase();
        dataStore.ensureIndexes();
        dataStore.getDB().createCollection("stores", null);
        dataStore.getDB().createCollection("orders", null);

        // Load db with products
        // Remember to start your local mongo instance =)))
        try (Stream<String> stream = Files.lines(Paths.get("src/test/java/test_data/stores.txt"))) {
            MongoCollection<Document> collection = client.getDatabase("beaverDB").getCollection("stores");
            stream.map(Document::parse)
                    .forEach(collection::insertOne);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<String> stream = Files.lines(Paths.get("src/test/java/test_data/store_orders.txt"))) {
            MongoCollection<Document> collection = client.getDatabase("beaverDB").getCollection("orders");
            stream.map(Document::parse)
                    .forEach(collection::insertOne);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void print_orders() {
        final Query<Store> query = dataStore.createQuery(Store.class);
        final List<Store> stores = query.asList();
        stores.forEach(System.out::println);
    }



    @Test
    public void db_should_retrieve_a_list_of_stores() {
        final Query<Store> query = dataStore.createQuery(Store.class);
        final List<Store> stores = query.asList();
        assertEquals(3, stores.size()); //expected then acutal
    }


    @Test
    public void dao_should_return_list_of_stores() {
        final StoreDao dao = new StoreDao(dataStore);
        final List<Store> stores =  dao.getAllStores();
        assertEquals(3, stores.size());
    }


    @Test
    public void controller_should_return_a_list_of_stores() {
        final StoreDao dao = new StoreDao(dataStore);
        final StoreController controller = new StoreController(dao);
        final List<Store> stores =  controller.getAllStores();
        assertEquals(3, stores.size());
    }

    @Test
    public void get_stores_by_id() {
        final StoreDao dao = new StoreDao(dataStore);
        final StoreController controller = new StoreController(dao);
        String id = "78970117-3715-4f91-8b4f-c4f3342f5a83";
        try {
            final Store store = controller.getStore(id);
            assertEquals(id, store.getId());
        } catch (StoreException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void get_stock_by_store_id() {
        final StoreDao dao = new StoreDao(dataStore);
        final StoreController controller = new StoreController(dao);
        String storeId = "78970117-3715-4f91-8b4f-c4f3342f5a84";
        String stockId = "78970117-3715-4f91-8b4f-c4f3342f5a83";
        try {
            final List<Stock> stock = controller.getStoreStock(storeId);
            assertEquals(stockId, stock.get(0).getProductId());
            System.out.println(stock);

        } catch (StoreException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void get_stock_by_query_params() {
        final StoreDao dao = new StoreDao(dataStore);
        final StoreController controller = new StoreController(dao);
        String storeID = "78970117-3715-4f91-8b4f-c4f3342f5a84";
        String productIDs = "78970117-3715-4f91-8b4f-c4f3342f5a83,78970117-3715-4f91-8b4f-c4f3342f5a82";
        String fromBad = "2018-05-18T10:57+02:00"; //1526633863
        String from = "2018-05-18T02:00+02:00"; // 1526608800
        String to = "2018-05-27T20:06+02:00"; //1527451560

        final List<Stock> stock = controller.getStockByQueryParams(storeID, from, to, productIDs );

        //Amount of products returned
        assertEquals(2, stock.size());

        System.out.println("Stock id: " + stock + "\n" + "Amount of stock found: " + stock.size());
    }


    @Test
    public void get_orders_by_query_params() {

        String storeID = "69999998-3715-4f91-8b4f-c4f3342f5a83";
        String productIDs = "12345678-3715-4f91-8b4f-c4f3342f5a83,78970117-3715-4f91-8b4f-c4f3342f5a82";
        int from = 1526633860; // 1526608800
        int to = 1526688002; //1527451560

        String products[] = productIDs.split(",");
        final List<Order> orders = dataStore.createQuery(Order.class)
                .field("storeID")
                .contains(storeID)
                .filter("timestamp >", from)
                .filter("timestamp <", to)
                .field("products.productID")
                .hasAnyOf(Arrays.asList(products)).asList();

        for(Order order : orders) {
            System.out.println("///////////Order number: " + order);
        }

        assertEquals(2, orders.size());
    }

    @Test
    public void get_orders_by_query_params_from_controller() {

        final StoreDao dao = new StoreDao(dataStore);
        final StoreController controller = new StoreController(dao);

        String storeID = "69999998-3715-4f91-8b4f-c4f3342f5a83";
        String productIDs = "12345678-3715-4f91-8b4f-c4f3342f5a83,78970117-3715-4f91-8b4f-c4f3342f5a82";
        String from = "2016-04-27T20:06+02:00"; // "+" (if sent via postman) --> %2B
        String to = "2019-04-27T20:06+02:00"; //
        Map<String, String[]> map = new HashMap<>();
        map.put("productIDs", new String[] {productIDs});
        map.put("from", new String[] {from});
        map.put("to", new String[] {to});

        List<Order> orders = controller.getOrdersByQueryParams(storeID, map);

        for(Order order : orders) {
            System.out.println("///////////Order number: " + order);
        }

        assertEquals(3, orders.size());
    }


}
