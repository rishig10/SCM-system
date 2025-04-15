import java.util.*;

public class Supplier implements ISupplier {
    private int supplierID;
    private String name;
    private List<IProduct> products;

    public Supplier(int supplierID, String name) {
        this.supplierID = supplierID;
        this.name = name;
        this.products = new ArrayList<>();
    }

    @Override
    public void addProduct(IProduct product) {
        products.add(product);
    }

    @Override
    public List<IProduct> getProducts() { return products; }
    
    @Override
    public int getSupplierID() { return supplierID; }
    
    @Override
    public String getName() { return name; }
}
