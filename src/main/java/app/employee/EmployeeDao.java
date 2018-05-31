package app.employee;
import app.order.Order;
import app.util.Utils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;
import java.util.Map;

public class EmployeeDao {
    private Datastore datastore;

    public EmployeeDao(Datastore datastore) {
        this.datastore = datastore;
    }

    public List<Employee> getAllEmployees() {
        final Query<Employee> query = datastore.createQuery(Employee.class);
        final List<Employee> employees = query.asList();
        return employees;
    }

    public Employee getEmployeeById(String id) {
        final Query<Employee> query = datastore.createQuery(Employee.class);
        final Employee employee = query.field("id").equal(id).get();
        return employee;
    }

    public void createEmployee(Employee employee) throws CommentException {
        for (Comment comment : employee.getComments()){
            if (comment.getComment().length() > 300){
                employee.getComments().remove(comment);
                throw new CommentException("Comment exceeds 300 characters");
            }
        }
        datastore.save(employee);
    }

    public List<Employee> getEmployeesFromQueryParams(Map<String, String[]> params) {
        final Query<Employee> query = datastore.createQuery(Employee.class);

        params.forEach((k, v) -> {
            switch(k) {
                case "from": addStartDateTimestampToQuery(query, v);
                    break;
                case "to": addEndDateTimestampToQuery(query, v);
                    break;
            }
        });
        final List<Employee> employees = query.asList();
        return employees;
    }

    private Query<Employee> addStartDateTimestampToQuery(Query<Employee> query, String[] params) {
        for (String item : params) {
            query.filter("employee.details.employmentHistory.startDate >=", Utils.getUnixTimestampFromDateString(item));
        }
        return query;
    }

    private Query<Employee> addEndDateTimestampToQuery(Query<Employee> query, String[] params) {
        for (String item : params) {
            query.filter("employee.details.employmentHistory.endDate <=", Utils.getUnixTimestampFromDateString(item));
        }
        return query;
    }
}
