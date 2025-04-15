import java.util.*;

public class SystemManager implements ISystemManager {
    private List<IOrder> orders;
    private List<ISupplier> suppliers;

    public SystemManager() {
        this.orders = new ArrayList<>();
        this.suppliers = new ArrayList<>();
    }

    @Override
    public void startup() {
        System.out.println("System is starting up...");
    }

    @Override
    public void processOrder(IOrder order) {
        for (ISupplier supplier : suppliers) {
            for (IProduct product : supplier.getProducts()) {
                if (product.getProductID() == order.getProductID() && product.checkAvailability(order.getQuantity())) {
                    product.updateStock(order.getQuantity());
                    order.updateStatus("Shipped");
                    return;
                }
            }
        }
        order.updateStatus("Failed");
    }

    @Override
    public void addSupplier(ISupplier supplier) {
        suppliers.add(supplier);
    }

    @Override
    public List<ISupplier> getSuppliers() { return suppliers; }
}

