import java.util.*;

public class Product implements IProduct {
    private int productID;
    private String name;
    private double price;
    private int stockQuantity;

    public Product(int productID, String name, double price, int stockQuantity) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    @Override
    public boolean checkAvailability(int quantity) {
        return stockQuantity >= quantity;
    }

    @Override
    public void updateStock(int quantity) {
        stockQuantity -= quantity;
    }

    @Override
    public int getProductID() { return productID; }
    
    @Override
    public String getName() { return name; }
    
    @Override
    public double getPrice() { return price; }
    
    @Override
    public int getStockQuantity() { return stockQuantity; }
}
