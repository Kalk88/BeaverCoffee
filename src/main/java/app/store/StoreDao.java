package app.store;


import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.LinkedList;
import java.util.List;

public class StoreDao {
    private Datastore datastore;

    public StoreDao(Datastore dataStore) {
        this.datastore = dataStore;
    }


    public List<Store> getAllStores() {
        final Query<Store> query = datastore.createQuery(Store.class);
        return query.asList();
    }

    public Store getStore(String id) {
        final Query<Store> query = datastore.createQuery(Store.class);
        final List<Store> store = query.field("id").equal(id).asList();
        return store.get(0);
    }

    public LinkedList<Stock> getStoreStock(String storeID) {
        final Query<Store> query = datastore.createQuery(Store.class);
        final List<Store> store = query.field("id").equal(storeID).asList();
        return store.get(0).getStock();
    }
}
