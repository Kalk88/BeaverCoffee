package app.order;

import app.util.Utils;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class OrderController {
    private OrderDao dao;

    public OrderController(OrderDao dao) {
        this.dao = dao;
    }

    public List<Order> getAllOrders() {return dao.getAllOrders();}

    public List<Order> getOrdersByQueryParams(Map<String, String[]> params) {
        return dao.getOrdersFromQueryParams(params);
    }

    public Order getOrderById(String id) {
        return dao.getOrderById(id);
    }

    public String createOrder(String body) {
        Order order;
        order = new Gson().fromJson(body, Order.class);
        String uuid = Utils.getUUIDString();
        order.setId(uuid);
        dao.insertOrder(order);
        return uuid;
    }

    public void updateOrder(String body) {
        Order order;
        order = new Gson().fromJson(body, Order.class);
        Order orderToBeUpdated = getOrderById(order.getId());
        Order updatedOrder = orderToBeUpdated.overwriteOrder(order);
        dao.insertOrder(updatedOrder);
    }

    public void deleteOrder(String body) {
        Order order;
        order = new Gson().fromJson(body, Order.class);
        Order orderToBeDeleted = getOrderById(order.getId());
        dao.deleteOrder(orderToBeDeleted);
    }
}
