public class Order implements IOrder {
    private int orderID;
    private int retailerID;
    private int productID;
    private int quantity;
    private String status;

    public Order(int orderID, int retailerID, int productID, int quantity) {
        this.orderID = orderID;
        this.retailerID = retailerID;
        this.productID = productID;
        this.quantity = quantity;
        this.status = "Placed";
    }

    @Override
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    @Override
    public int getOrderID() { return orderID; }
    
    @Override
    public int getRetailerID() { return retailerID; }
    
    @Override
    public int getProductID() { return productID; }
    
    @Override
    public int getQuantity() { return quantity; }
    
    @Override
    public String getStatus() { return status; }
}
