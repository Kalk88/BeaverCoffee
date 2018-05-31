package app.store;


import app.order.Order;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StoreController {
    private StoreDao dao;

    public StoreController(StoreDao dao) {
        this.dao = dao;
    }

    public List<Store> getAllStores() {
        return dao.getAllStores();
    }

    public Store getStore(String id) throws StoreException {
        try {
            return dao.getStore(id);
        } catch (IndexOutOfBoundsException e) {
            throw new StoreException("Can not find store with that id");
        }
    }


    public List<Stock> getStoreStock(String storeID) throws StoreException {
        try{
            return dao.getStoreStock(storeID);
        } catch(IndexOutOfBoundsException e) {
            throw new StoreException("Can not find stock in store with that id");
        }
    }

    public List<Order> getOrdersByQueryParams(String id, int from, int to, String productIDs ) {
        return dao.getOrdersFromQueryParams(id, from, to, productIDs);
    }

    public List<Stock> getStockByQueryParams(String id,  String from, String to, String productIDs) {
        return dao.getStockFromQueryParams(id, from, to, productIDs);
    }

}
