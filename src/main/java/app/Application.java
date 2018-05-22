package app;

import app.order.OrderController;
import app.order.OrderDao;
import app.product.ProductController;
import app.product.ProductDao;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import static spark.Spark.*;

public class Application {

    public static void main(String[] args) {
        //Spark setup
        port(8080);

        //Morphia setup
        Morphia morphia = new Morphia();
        final MongoClient client = new MongoClient();
        morphia.mapPackage("com.babayaga.beavercoffee.product");
        morphia.mapPackage("com.babayaga.beavercoffee.order");
        morphia.mapPackage("com.babayaga.beavercoffee.customer");
        morphia.mapPackage("com.babayaga.beavercoffee.employee");
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

        // Get all orders or with query params
        get("api/orders", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                if (req.queryMap() != null){
                    return new Gson().toJson(orderController.getOrdersByQueryParams(req.queryMap().toMap()));
                } else {
                    return new Gson().toJson(orderController.getAllOrders());
                }
            } catch (Exception e) {
                res.status(400);
                e.printStackTrace();
                return "Error retrieving orders";
            }
        });

        // Get order with specific ID
        get("api/orders/:id", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                return new Gson().toJson(orderController.getOrderById(req.params("id")));
            } catch (Exception e) {
                res.status(400);
                return "Error retrieving order";
            }
        });

        post("api/orders", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                return new Gson().toJson(orderController.createOrder(req.body()));
            } catch (Exception e) {
                e.printStackTrace();
                res.status(400);
                return "Error retrieving order";
            }
        });
    }
}
