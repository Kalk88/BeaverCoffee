package app.employee;

import app.util.Utils;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class EmployeeController {
    private EmployeeDao dao;

    public EmployeeController(EmployeeDao dao) {
        this.dao = dao;
    }

    public Employee getEmployeeById(String id) {
        return dao.getEmployeeById(id);
    }

    public String createEmployee(String body) throws CommentException {
        Employee employee;
        employee = new Gson().fromJson(body, Employee.class);
        String uuid = Utils.getUUIDString();
        employee.setId(uuid);
        dao.createEmployee(employee);
        return uuid;
    }

    public List<Employee> getAllEmployees() {return dao.getAllEmployees();}

    public void updateEmployee(String body) throws CommentException {
        Employee employee;
        employee = new Gson().fromJson(body, Employee.class);
        Employee employeeToBeUpdated = getEmployeeById(employee.getId());
        Employee updatedEmployee = employeeToBeUpdated.overwriteEmployee(employee);
        dao.createEmployee(updatedEmployee);
    }

    public List<Employee> getEmployeesByQueryParams(Map<String, String[]> params) {
        return dao.getEmployeesFromQueryParams(params);
    }
}
