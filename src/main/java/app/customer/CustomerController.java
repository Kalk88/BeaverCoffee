package app.customer;

import app.util.Utils;
import com.google.gson.Gson;

import java.util.List;

public class CustomerController {
    private CustomerDao dao;

    public CustomerController(CustomerDao dao) {
        this.dao = dao;
    }

    /**
     * Returns a list of all customers
     * @return List<Customer>
     */
    public List<Customer> getAllCustomers() {
        return dao.getAllCustomers();
    }


    /**
     * Returns a Customer or throws a CustomerException if the customer does not exist.
     * @param id of Customer
     * @return Customer
     * @throws CustomerException
     */
    public Customer getCustomer(String id) throws CustomerException {
        try {
            return dao.getCustomer(id);
        } catch (IndexOutOfBoundsException e) {
            throw new CustomerException("Can not find customer with that id");
        }
    }

    public String createCustomer(String data) {
        Customer customer = new Gson().fromJson(data, Customer.class);
        customer.setId(Utils.getUUIDString());
        dao.insertCustomer(customer);
        return customer.getId();
    }
}
