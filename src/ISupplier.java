import java.util.List;

public interface ISupplier {
    void addProduct(IProduct product);
    List<IProduct> getProducts();
    int getSupplierID();
    String getName();
}
