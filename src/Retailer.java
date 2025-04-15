import java.util.*;

public class Retailer implements IRetailer {
    private int retailerID;
    private String name;
    private List<IOrder> orders;

    public Retailer(int retailerID, String name) {
        this.retailerID = retailerID;
        this.name = name;
        this.orders = new ArrayList<>();
    }

    @Override
    public void placeOrder(IOrder order) {
        orders.add(order);
    }

    @Override
    public int getRetailerID() { return retailerID; }
    
    @Override
    public String getName() { return name; }
    
    @Override
    public List<IOrder> getOrders() { return orders; }
}
