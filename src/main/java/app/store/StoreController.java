package app.store;


import java.util.LinkedList;
import java.util.List;

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


    public LinkedList<Stock> getStoreStock(String storeID) throws StoreException {
        try{
            return dao.getStoreStock(storeID);
        } catch(IndexOutOfBoundsException e) {
            throw new StoreException("Can not find stock in store with that id");
        }
    }
}
