package app;

import app.customer.CustomerController;
import app.customer.CustomerDao;
import app.customer.CustomerException;
import app.employee.EmployeeController;
import app.employee.EmployeeDao;
import app.order.Order;
import app.order.OrderController;
import app.order.OrderDao;
import app.product.ProductController;
import app.product.ProductDao;
import app.store.StoreController;
import app.store.StoreDao;
import app.store.StoreException;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static spark.Spark.*;

public class Application {

    public void start() {
        //Spark setup
        port(8080);

        //Morphia setup
        Morphia morphia = new Morphia();
        final MongoClient client = new MongoClient();
        morphia.mapPackage("com.babayaga.beavercoffee.product");
        morphia.mapPackage("com.babayaga.beavercoffee.order");
        morphia.mapPackage("com.babayaga.beavercoffee.customer");
        morphia.mapPackage("com.babayaga.beavercoffee.employee");
        morphia.mapPackage("com.babayaga.beavercoffee.store");
        Datastore dataStore = morphia.createDatastore(client, "beaverDB");

        // Initilize and seed database. Ugliest shit I have ever made, but w/e.
        dataStore.getDB().dropDatabase();
        dataStore.ensureIndexes();

        // Customer
        try (Stream<String> stream = Files.lines(Paths.get("src/resources/seed_data/customers.txt"))) {
            MongoCollection<Document> collection = client.getDatabase("beaverDB").getCollection("customers");
            stream.map(Document::parse)
                    .forEach(collection::insertOne);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Product
        try (Stream<String> stream = Files.lines(Paths.get("src/resources/seed_data/products_eng.txt"))) {
            MongoCollection<Document> collection = client.getDatabase("beaverDB").getCollection("products");
            stream.map(Document::parse)
                    .forEach(collection::insertOne);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Order
        try (Stream<String> stream = Files.lines(Paths.get("src/resources/seed_data/orders.txt"))) {
            MongoCollection<Document> collection = client.getDatabase("beaverDB").getCollection("orders");
            stream.map(Document::parse)
                    .forEach(collection::insertOne);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Employee
        try (Stream<String> stream = Files.lines(Paths.get("src/resources/seed_data/employees.txt"))) {
            MongoCollection<Document> collection = client.getDatabase("beaverDB").getCollection("employees");
            stream.map(Document::parse)
                    .forEach(collection::insertOne);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Store
        try (Stream<String> stream = Files.lines(Paths.get("src/resources/seed_data/store_orders.txt"))) {
            MongoCollection<Document> collection = client.getDatabase("beaverDB").getCollection("stores");
            stream.map(Document::parse)
                    .forEach(collection::insertOne);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<String> stream = Files.lines(Paths.get("src/resources/seed_data/stores.txt"))) {
            MongoCollection<Document> collection = client.getDatabase("beaverDB").getCollection("stores");
            stream.map(Document::parse)
                    .forEach(collection::insertOne);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // init Controllers
        final ProductController productController = new ProductController(new ProductDao(dataStore));
        final OrderController orderController = new OrderController(new OrderDao(dataStore));
        final CustomerController customerController = new CustomerController(new CustomerDao(dataStore));
        final StoreController storeController = new StoreController(new StoreDao(dataStore));
        final EmployeeController employeeController = new EmployeeController(new EmployeeDao(dataStore));

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
                Order order = orderController.getOrderById(req.params("id"));
                if (order == null) {
                    res.status(204);
                    return "";
                }
                return new Gson().toJson(order, Order.class);

            } catch (Exception e) {
                res.status(400);
                return "Error retrieving order";
            }
        });

        post("api/orders", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                String id = orderController.createOrder(req.body());
                return String.format("{\"id\":\"%s\"}", id);
            } catch (Exception e) {
                e.printStackTrace();
                res.status(400);
                return "Error retrieving order";
            }
        });

        put("api/orders/:id", (req, res) -> {
            try {
                orderController.updateOrder(req.body(), req.params("id"));
                res.status(204);
                return "";
            } catch (Exception e) {
                e.printStackTrace();
                res.status(400);
                return "Error updating order";
            }
        });

        delete("api/orders/:id", (req, res) -> {
            try {
                orderController.deleteOrder(req.params("id"));
                res.status(204);
                return "";
            } catch (Exception e) {
                e.printStackTrace();
                res.status(400);
                return "Error deleting order";
            }
        });

        //Customers
        get("api/customers", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                return new Gson().toJson(customerController.getAllCustomers());
            } catch (Exception e) {
                res.status(400);
                return "Error retrieving customers";
            }
        });


        post("api/customers", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                String id = customerController.createCustomer(req.body());
                return String.format("{\"id\":\"%s\"}", id);
            } catch (Exception e) {
                e.printStackTrace();
                res.status(400);
                return "Error adding customer";
            }
        });

        //Customers
        get("api/customers/:id", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                return new Gson().toJson(customerController.getCustomer(req.params(":id")));
            } catch (CustomerException e) {
                res.status(400);
                return e.getMessage();
            }
        });

        //Stores
        get("api/stores", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                return new Gson().toJson(storeController.getAllStores());
            } catch (Exception e) {
                res.status(400);
                return "Error retrieving customers";
            }
        });

        //Store
        get("api/stores/:id", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                return new Gson().toJson(storeController.getStore(req.params(":id")));
            } catch (StoreException e) {
                res.status(400);
                return e.getMessage();
            }
        });

        //Store
        get("api/stores/:id/stock", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                return new Gson().toJson(storeController.getStoreStock(req.params(":id")));
            } catch (StoreException e) {
                res.status(400);
                return e.getMessage();
            }
        });

        //Stock reports
        get("api/stores/:id/stock/reports", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                if (req.queryMap() != null){
                    return new Gson().toJson(storeController.getStockByQueryParams(req.params("id"),
                            req.params("from"), req.params("to"), req.params("productIDs")));
                } else {
                    return "Error retrieving stock";
                }
            } catch (Exception e) {
                res.status(400);
                e.printStackTrace();
                return "Error retrieving stock";
            }
        });
                  
        // Get all orders within some time
        get("api/stores/:id/reports", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                return new Gson().toJson(storeController.getOrdersByQueryParams(req.params("id") ,req.queryMap().toMap()));
            } catch (Exception e) {
                res.status(400);
                e.printStackTrace();
                return "Error retrieving orders";
            }
        });
      
        //Employee
        // Get employee with specific ID
        get("api/employees/:id", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                return new Gson().toJson(employeeController.getEmployeeById(req.params("id")));
            } catch (Exception e) {
                res.status(400);
                return "Error retrieving employee";
            }
        });

        //Get all employees
        get("api/employees", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                if (req.queryMap() != null){
                    return new Gson().toJson(employeeController.getEmployeesByQueryParams(req.queryMap().toMap()));
                } else {
                    return new Gson().toJson(employeeController.getAllEmployees());
                }
            } catch (Exception e) {
                res.status(400);
                e.printStackTrace();
                return "Error retrieving orders";
            }
        });

        //Create Employee
        post("api/employees", (req, res) -> {
            try {
                res.header("content-type", "application/json");
                String id = employeeController.createEmployee(req.body());
                return String.format("{\"id\":\"%s\"}", id);
            } catch (Exception e) {
                e.printStackTrace();
                res.status(400);
                return "Error retrieving employee";
            }
        });

        //Update Employee
        put("api/employees/:id", (req, res) -> {
            try {
                employeeController.updateEmployee(req.body());
                res.status(204);
                return "";
            } catch (Exception e) {
                e.printStackTrace();
                res.status(400);
                return "Error updating employee";
            }
        });
    }
}
