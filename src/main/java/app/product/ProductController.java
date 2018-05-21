package app.product;

import java.util.List;

public class ProductController {
    private ProductDao dao;

    public ProductController(ProductDao dao) {
        this.dao = dao;
    }

    public List<Product> getAllProducts() {
        return dao.getAllProducts();
    }
}
