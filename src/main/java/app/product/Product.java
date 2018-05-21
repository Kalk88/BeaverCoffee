package app.product;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Base model for BeaverCoffee products.
 *
 */

@Entity("products")
@Indexes(
        @Index(value="type", fields =@Field("type"))
)
public class Product {
    @Id
    private ObjectId _id;
    private String id;
    private double quantity;
    private Map<String,String> name;
    private Map<String,String> price;
    private String type;

    private Product() {}

    /**
     *
     * @return string
     */
    public String toString() {
        return _id + " " + id + " " + quantity + " " + name + " " + price + " " + type;
    }
}
