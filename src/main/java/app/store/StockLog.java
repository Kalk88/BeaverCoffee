package app.store;

import java.util.List;

/**
 * Created by kemkoi on 5/30/18.
 */
public class StockLog {

    private String id;
    private String timestamp;
    private List<Stock> stock;

    public String getStoreID() {
        return id;
    }

    public void setStoreID(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<Stock> getStock() {
        return stock;
    }

    public void setStock(List<Stock> stock) {
        this.stock = stock;
    }

}
