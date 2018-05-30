package app.store;

import app.util.Utils;
import com.google.gson.Gson;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

    public List<Stock> getStockFromQueryParams(String id, String from, String to, String productIDs) {

        Gson gson = new Gson();
        StockLog[] stockLogs = new StockLog[0];
        try {
            stockLogs = gson.fromJson(new FileReader("/Users/kemkoi/Desktop/BeaverCoffee/src/test/java/test_data/stock_log.txt"), StockLog[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Stock> finalStockList = new LinkedList<>();
        String[] products = productIDs.split(",");

        for(StockLog log : stockLogs) {
            if(log.getStoreID().equals(id)) {
                for(Stock stock : log.getStock()) {
                    for(String productID : products) {
                        if(stock.getProductId().equals(productID)) {
                            Date fromDate = new Date(Utils.getUnixTimestampFromDateString(from));
                            Date toDate = new Date(Utils.getUnixTimestampFromDateString(to));
                            Date logDate = new Date(Long.parseLong(log.getTimestamp()));

                            if(isInDateRange(fromDate, toDate, logDate)) {

                                finalStockList.add(stock);
                            }
                        }
                    }
                }
            }
        }

        return finalStockList;
    }

    private boolean isInDateRange(Date fromDate, Date toDate, Date logDate) {
        return logDate.after(fromDate) && logDate.before(toDate);
    }

}
