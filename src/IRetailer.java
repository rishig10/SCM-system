import java.util.List;

public interface IRetailer {
    void placeOrder(IOrder order);
    int getRetailerID();
    String getName();
    List<IOrder> getOrders();
}
