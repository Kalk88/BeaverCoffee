package app.order;

import java.util.List;

public class OrderController {
    private OrderDao dao;

    public OrderController(OrderDao dao) {
        this.dao = dao;
    }

    public List<Order> getAllOrders() {return dao.getAllOrders();}
}
