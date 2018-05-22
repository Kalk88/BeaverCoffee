package app.customer;

import java.util.List;

public class CustomerController {
    private CustomerDao dao;

    public CustomerController(CustomerDao dao) {
        this.dao = dao;
    }

    public List<Customer> getAllCustomers() {
        return dao.getAllCustomers();
    }

    public Customer getCustomer(String id) throws CustomerException {
        try {
            return dao.getCustomer(id);
        } catch (IndexOutOfBoundsException e) {
            throw new CustomerException("Can not find customer with that id");
        }
    }
}
