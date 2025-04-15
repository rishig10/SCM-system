public interface IProduct {
    boolean checkAvailability(int quantity);
    void updateStock(int quantity);
    int getProductID();
    String getName();
    double getPrice();
    int getStockQuantity();
}
