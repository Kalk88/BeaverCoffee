package app.product;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class ProductDao {
    private Datastore datastore;

    public ProductDao(Datastore datastore) {
        this.datastore = datastore;
    }

    public List<Product> getAllProducts() {
        final Query<Product> query = datastore.createQuery(Product.class);
        final List<Product> products = query.asList();
        return products;
    }
}
