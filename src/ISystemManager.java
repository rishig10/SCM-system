import java.util.List;

public interface ISystemManager {
    void startup();
    void processOrder(IOrder order);
    void addSupplier(ISupplier supplier);
    List<ISupplier> getSuppliers();
}
