public interface IOrder {
    void updateStatus(String newStatus);
    int getOrderID();
    int getRetailerID();
    int getProductID();
    int getQuantity();
    String getStatus();
}
