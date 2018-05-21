package app;

import app.order.OrderController;
import app.order.OrderDao;
import app.product.Product;
import app.product.ProductController;
import app.product.ProductDao;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import org.eclipse.jetty.util.log.Log;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.List;

import static spark.Spark.*;

public class Application {

    public static void main(String[] args) {
        //Spark setup
        port(8080);

        //Morphia setup
        Morphia morphia = new Morphia();
        final MongoClient client = new MongoClient();
        morphia.mapPackage("com.babayaga.beavercoffee.product"); // Can be called several times with diffrent packages if needed
        morphia.mapPackage("com.babayaga.beavercoffee.order"); // Can be called several times with diffrent packages if needed
        morphia.mapPackage("com.babayaga.beavercoffee.customer"); // Can be called several times with diffrent packages if needed
        morphia.mapPackage("com.babayaga.beavercoffee.employee"); // Can be called several times with diffrent packages if needed
        morphia.mapPackage("com.babayaga.beavercoffee.store"); // Can be called several times with diffrent packages if needed
        Datastore dataStore = morphia.createDatastore(client, "beaverDB");

        // init Controllers
        ProductController productController = new ProductController(new ProductDao(dataStore));
        OrderController orderController = new OrderController(new OrderDao(dataStore));

        // ROUTES
        // Products
        get("api/products", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                return new Gson().toJson(productController.getAllProducts());
            } catch (Exception e) {
                res.status(400);
                return "Error retrieving products";
            }
        });

        // Orders
        get("api/orders", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                return new Gson().toJson(orderController.getAllOrders());
            } catch (Exception e) {
                res.status(400);
                return "Error retrieving orders";
            }
        });
    }
}
