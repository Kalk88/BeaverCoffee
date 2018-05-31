package app;

import app.customer.CustomerController;
import app.customer.CustomerDao;
import app.customer.CustomerException;
import app.employee.EmployeeController;
import app.employee.EmployeeDao;
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
        final ProductController productController = new ProductController(new ProductDao(dataStore));
        final OrderController orderController = new OrderController(new OrderDao(dataStore));
        final CustomerController customerController = new CustomerController(new CustomerDao(dataStore));
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
                return new Gson().toJson(orderController.getOrderById(req.params("id")));
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
                orderController.updateOrder(req.body());
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
                orderController.deleteOrder(req.body());
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
