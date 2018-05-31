import app.employee.Employee;
import app.employee.EmployeeController;
import app.employee.EmployeeDao;
import app.order.Order;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import test_data.dummy_data.EmployeeDummy;
import test_data.dummy_data.OrderDummy;

import static org.junit.Assert.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTest {
    private Morphia morphia;
    private Datastore datastore;
    private MongoClient client;

    @Before
    public void setup() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "WARN");
        morphia = new Morphia();
        client = new MongoClient();
        morphia.mapPackage("com.babayaga.beavercoffee.order");
        datastore = morphia.createDatastore(client, "beaverDB");
        datastore.getDB().dropDatabase();
        datastore.getDB().createCollection("employees", null);
        datastore.ensureIndexes();

        try (Stream<String> stream = Files.lines(Paths.get("src/test/java/test_data/employees.txt"))) {
            MongoCollection<Document> collection = client.getDatabase("beaverDB").getCollection("employees");
            stream.map(Document::parse)
                    .forEach(collection::insertOne);

            System.out.println(collection.count());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * View Employees
     */
    @Test
    public void print_employees() {
        final Query<Employee> query = datastore.createQuery(Employee.class);
        final List<Employee> employees = query.asList();
        employees.forEach(System.out::println);

    }

    @Test
    public void get_all_employees_from_api() {
        final EmployeeDao dao = new EmployeeDao(datastore);
        final EmployeeController controller = new EmployeeController(dao);
        final List<Employee> employees = controller.getAllEmployees();
        assertEquals(2, employees.size());
    }

    @Test
    public void get_employee_by_id() {
        final EmployeeDao dao = new EmployeeDao(datastore);
        final EmployeeController controller = new EmployeeController(dao);
        String id = "78970117-3715-4f91-8b4f-c4f3342f5a83";
        final Employee employee = controller.getEmployeeById(id);
        assertEquals(id, employee.getId());
    }

    @Test
    public void should_update_employee_with_new_values() {
        final EmployeeDao dao = new EmployeeDao(datastore);
        final EmployeeController controller = new EmployeeController(dao);
        Employee employeeToBeUpdated = controller.getEmployeeById("78970117-3715-4f91-8b4f-c4f3342f5a83");
        Employee employee = new Gson().fromJson(EmployeeDummy.updatedData, Employee.class);
        Employee updatedEmployee = employeeToBeUpdated.overwriteEmployee(employee);
        assertEquals(updatedEmployee.get_id(), employeeToBeUpdated.get_id());
        dao.createEmployee(updatedEmployee);
        assertEquals(2, datastore.getCollection(Employee.class).count());
    }

    @Test
    public void get_employees_during_specified_timeperiod() {
        final Query<Employee> query = datastore.createQuery(Employee.class);
        query.filter("details.employmentHistory.startTimestamp >", 1526633860);
        query.filter("details.employmentHistory.endTimestamp <", 1526947201);
        final List<Employee> employees = query.asList();
        assertEquals(2, employees.size());
    }

    @Test
    public void insert_employee_in_employees_collection() {
        Employee employee = EmployeeDummy.GetDummyEmployee();
        datastore.save(employee);
        final Query<Employee> query = datastore.createQuery(Employee.class);
        final List<Employee> employees = query.filter("id", "78970117-3715-4f91-8b4f-c4f3342fabcd").asList();
        Assert.assertEquals(1, employees.size());
    }
}

