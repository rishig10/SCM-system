/*
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SupplyChainManagementSystemUI extends JFrame {
    private ISystemManager systemManager;
    private List<IRetailer> retailers;
    
    // UI Components
    private JTabbedPane tabbedPane;
    private JPanel suppliersPanel, productsPanel, retailersPanel, ordersPanel;
    private JTable suppliersTable, productsTable, retailersTable, ordersTable;
    private DefaultTableModel suppliersTableModel, productsTableModel, retailersTableModel, ordersTableModel;
    
    public SupplyChainManagementSystemUI() {
        // Initialize business logic
        systemManager = new SystemManager();
        retailers = new ArrayList<>();
        
        // Initialize UI
        setTitle("Supply Chain Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Create panels
        createSuppliersPanel();
        createProductsPanel();
        createRetailersPanel();
        createOrdersPanel();
        
        // Add panels to tabbed pane
        tabbedPane.addTab("Suppliers", suppliersPanel);
        tabbedPane.addTab("Products", productsPanel);
        tabbedPane.addTab("Retailers", retailersPanel);
        tabbedPane.addTab("Orders", ordersPanel);
        
        // Add tabbed pane to frame
        add(tabbedPane);
        
        // Start the system
        systemManager.startup();
        
        // Show the frame
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void createSuppliersPanel() {
        suppliersPanel = new JPanel(new BorderLayout());
        
        // Create table model
        suppliersTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Name"}
        );
        
        // Create table
        suppliersTable = new JTable(suppliersTableModel);
        JScrollPane scrollPane = new JScrollPane(suppliersTable);
        suppliersPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Supplier");
        buttonPanel.add(addButton);
        suppliersPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddSupplierDialog();
            }
        });
    }
    
    private void createProductsPanel() {
        productsPanel = new JPanel(new BorderLayout());
        
        // Create table model
        productsTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Name", "Price", "Stock", "Supplier"}
        );
        
        // Create table
        productsTable = new JTable(productsTableModel);
        JScrollPane scrollPane = new JScrollPane(productsTable);
        productsPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Product");
        buttonPanel.add(addButton);
        productsPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddProductDialog();
            }
        });
    }
    
    private void createRetailersPanel() {
        retailersPanel = new JPanel(new BorderLayout());
        
        // Create table model
        retailersTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Name"}
        );
        
        // Create table
        retailersTable = new JTable(retailersTableModel);
        JScrollPane scrollPane = new JScrollPane(retailersTable);
        retailersPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Retailer");
        buttonPanel.add(addButton);
        retailersPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddRetailerDialog();
            }
        });
    }
    
    private void createOrdersPanel() {
        ordersPanel = new JPanel(new BorderLayout());
        
        // Create table model
        ordersTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Retailer", "Product", "Quantity", "Status"}
        );
        
        // Create table
        ordersTable = new JTable(ordersTableModel);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        ordersPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Place Order");
        JButton processButton = new JButton("Process Orders");
        buttonPanel.add(addButton);
        buttonPanel.add(processButton);
        ordersPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPlaceOrderDialog();
            }
        });
        
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processOrders();
                refreshOrdersTable();
                JOptionPane.showMessageDialog(SupplyChainManagementSystemUI.this, 
                                             "Orders processed successfully!");
            }
        });
    }
    
    private void showAddSupplierDialog() {
        JTextField idField = new JTextField(10);
        JTextField nameField = new JTextField(20);
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Supplier ID:"));
        panel.add(idField);
        panel.add(new JLabel("Supplier Name:"));
        panel.add(nameField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Supplier", 
                                                  JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                
                ISupplier supplier = new Supplier(id, name);
                systemManager.addSupplier(supplier);
                
                // Refresh table
                suppliersTableModel.addRow(new Object[]{id, name});
                
                JOptionPane.showMessageDialog(this, "Supplier added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid ID format!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showAddProductDialog() {
        // Get suppliers for dropdown
        List<ISupplier> suppliers = systemManager.getSuppliers();
        if (suppliers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add a supplier first!", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] supplierNames = new String[suppliers.size()];
        for (int i = 0; i < suppliers.size(); i++) {
            supplierNames[i] = suppliers.get(i).getSupplierID() + " - " + suppliers.get(i).getName();
        }
        
        JTextField idField = new JTextField(10);
        JTextField nameField = new JTextField(20);
        JTextField priceField = new JTextField(10);
        JTextField stockField = new JTextField(10);
        JComboBox<String> supplierComboBox = new JComboBox<>(supplierNames);
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Product ID:"));
        panel.add(idField);
        panel.add(new JLabel("Product Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Stock Quantity:"));
        panel.add(stockField);
        panel.add(new JLabel("Supplier:"));
        panel.add(supplierComboBox);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Product", 
                                                  JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                
                int selectedIndex = supplierComboBox.getSelectedIndex();
                ISupplier supplier = suppliers.get(selectedIndex);
                
                IProduct product = new Product(id, name, price, stock);
                supplier.addProduct(product);
                
                // Refresh table
                productsTableModel.addRow(new Object[]{id, name, price, stock, supplier.getName()});
                
                JOptionPane.showMessageDialog(this, "Product added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid number format!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showAddRetailerDialog() {
        JTextField idField = new JTextField(10);
        JTextField nameField = new JTextField(20);
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Retailer ID:"));
        panel.add(idField);
        panel.add(new JLabel("Retailer Name:"));
        panel.add(nameField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Retailer", 
                                                  JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                
                IRetailer retailer = new Retailer(id, name);
                retailers.add(retailer);
                
                // Refresh table
                retailersTableModel.addRow(new Object[]{id, name});
                
                JOptionPane.showMessageDialog(this, "Retailer added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid ID format!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showPlaceOrderDialog() {
        // Check if retailers exist
        if (retailers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add a retailer first!", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get all products from all suppliers
        List<IProduct> allProducts = new ArrayList<>();
        List<ISupplier> suppliers = systemManager.getSuppliers();
        
        if (suppliers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add suppliers and products first!", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        for (ISupplier supplier : suppliers) {
            allProducts.addAll(supplier.getProducts());
        }
        
        if (allProducts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add products first!", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Prepare drop-downs
        String[] retailerNames = new String[retailers.size()];
        for (int i = 0; i < retailers.size(); i++) {
            retailerNames[i] = retailers.get(i).getRetailerID() + " - " + retailers.get(i).getName();
        }
        
        String[] productNames = new String[allProducts.size()];
        for (int i = 0; i < allProducts.size(); i++) {
            productNames[i] = allProducts.get(i).getProductID() + " - " + allProducts.get(i).getName();
        }
        
        JComboBox<String> retailerComboBox = new JComboBox<>(retailerNames);
        JComboBox<String> productComboBox = new JComboBox<>(productNames);
        JTextField quantityField = new JTextField(10);
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Retailer:"));
        panel.add(retailerComboBox);
        panel.add(new JLabel("Product:"));
        panel.add(productComboBox);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Place Order", 
                                                  JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                
                int selectedRetailerIndex = retailerComboBox.getSelectedIndex();
                IRetailer retailer = retailers.get(selectedRetailerIndex);
                
                int selectedProductIndex = productComboBox.getSelectedIndex();
                IProduct product = allProducts.get(selectedProductIndex);
                
                // Generate order ID
                int orderId = 1;
                for (IRetailer r : retailers) {
                    orderId += r.getOrders().size();
                }
                
                IOrder order = new Order(orderId, retailer.getRetailerID(), product.getProductID(), quantity);
                retailer.placeOrder(order);
                
                // Refresh orders table
                refreshOrdersTable();
                
                JOptionPane.showMessageDialog(this, "Order placed successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid quantity format!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void processOrders() {
        for (IRetailer retailer : retailers) {
            for (IOrder order : retailer.getOrders()) {
                systemManager.processOrder(order);
            }
        }
    }
    
    private void refreshOrdersTable() {
        // Clear table
        ordersTableModel.setRowCount(0);
        
        // Refill with current data
        for (IRetailer retailer : retailers) {
            for (IOrder order : retailer.getOrders()) {
                ordersTableModel.addRow(new Object[]{
                    order.getOrderID(),
                    retailer.getName(),
                    order.getProductID(),
                    order.getQuantity(),
                    order.getStatus()
                });
            }
        }
    }
    
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SupplyChainManagementSystemUI();
            }
        });
    }
}
*/
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SupplyChainManagementSystemUI extends JFrame {
    private ISystemManager systemManager;
    private List<IRetailer> retailers;
    
    // UI Components
    private JTabbedPane tabbedPane;
    private JPanel suppliersPanel, productsPanel, retailersPanel, ordersPanel;
    private JTable suppliersTable, productsTable, retailersTable, ordersTable;
    private DefaultTableModel suppliersTableModel, productsTableModel, retailersTableModel, ordersTableModel;
    
    public SupplyChainManagementSystemUI() {
        // Initialize business logic
        systemManager = new SystemManager();
        retailers = new ArrayList<>();
        
        // Initialize UI
        setTitle("Supply Chain Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Create panels
        createSuppliersPanel();
        createProductsPanel();
        createRetailersPanel();
        createOrdersPanel();
        
        // Add panels to tabbed pane
        tabbedPane.addTab("Suppliers", suppliersPanel);
        tabbedPane.addTab("Products", productsPanel);
        tabbedPane.addTab("Retailers", retailersPanel);
        tabbedPane.addTab("Orders", ordersPanel);
        
        // Add tabbed pane to frame
        add(tabbedPane);
        
        // Start the system
        systemManager.startup();
        
        // Show the frame
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void createSuppliersPanel() {
        suppliersPanel = new JPanel(new BorderLayout());
        
        // Create table model
        suppliersTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Name"}
        );
        
        // Create table
        suppliersTable = new JTable(suppliersTableModel);
        JScrollPane scrollPane = new JScrollPane(suppliersTable);
        suppliersPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Supplier");
        buttonPanel.add(addButton);
        suppliersPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddSupplierDialog();
            }
        });
    }
    
    private void createProductsPanel() {
        productsPanel = new JPanel(new BorderLayout());
        
        // Create table model
        productsTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Name", "Price", "Stock", "Supplier"}
        );
        
        // Create table
        productsTable = new JTable(productsTableModel);
        JScrollPane scrollPane = new JScrollPane(productsTable);
        productsPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Product");
        JButton refreshButton = new JButton("Refresh");
        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);
        productsPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddProductDialog();
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshProductsTable();
            }
        });
    }
    
    private void createRetailersPanel() {
        retailersPanel = new JPanel(new BorderLayout());
        
        // Create table model
        retailersTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Name"}
        );
        
        // Create table
        retailersTable = new JTable(retailersTableModel);
        JScrollPane scrollPane = new JScrollPane(retailersTable);
        retailersPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Retailer");
        buttonPanel.add(addButton);
        retailersPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddRetailerDialog();
            }
        });
    }
    
    private void createOrdersPanel() {
        ordersPanel = new JPanel(new BorderLayout());
        
        // Create table model
        ordersTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Retailer", "Product", "Quantity", "Status"}
        );
        
        // Create table
        ordersTable = new JTable(ordersTableModel);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        ordersPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Place Order");
        JButton processButton = new JButton("Process Orders");
        buttonPanel.add(addButton);
        buttonPanel.add(processButton);
        ordersPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPlaceOrderDialog();
            }
        });
        
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processOrders();
                refreshOrdersTable();
                refreshProductsTable(); // Add this line to refresh products after processing orders
                JOptionPane.showMessageDialog(SupplyChainManagementSystemUI.this, 
                                             "Orders processed successfully!");
            }
        });
    }
    
    private void showAddSupplierDialog() {
        JTextField idField = new JTextField(10);
        JTextField nameField = new JTextField(20);
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Supplier ID:"));
        panel.add(idField);
        panel.add(new JLabel("Supplier Name:"));
        panel.add(nameField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Supplier", 
                                                  JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                
                ISupplier supplier = new Supplier(id, name);
                systemManager.addSupplier(supplier);
                
                // Refresh table
                suppliersTableModel.addRow(new Object[]{id, name});
                
                JOptionPane.showMessageDialog(this, "Supplier added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid ID format!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showAddProductDialog() {
        // Get suppliers for dropdown
        List<ISupplier> suppliers = systemManager.getSuppliers();
        if (suppliers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add a supplier first!", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] supplierNames = new String[suppliers.size()];
        for (int i = 0; i < suppliers.size(); i++) {
            supplierNames[i] = suppliers.get(i).getSupplierID() + " - " + suppliers.get(i).getName();
        }
        
        JTextField idField = new JTextField(10);
        JTextField nameField = new JTextField(20);
        JTextField priceField = new JTextField(10);
        JTextField stockField = new JTextField(10);
        JComboBox<String> supplierComboBox = new JComboBox<>(supplierNames);
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Product ID:"));
        panel.add(idField);
        panel.add(new JLabel("Product Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Stock Quantity:"));
        panel.add(stockField);
        panel.add(new JLabel("Supplier:"));
        panel.add(supplierComboBox);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Product", 
                                                  JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                
                int selectedIndex = supplierComboBox.getSelectedIndex();
                ISupplier supplier = suppliers.get(selectedIndex);
                
                IProduct product = new Product(id, name, price, stock);
                supplier.addProduct(product);
                
                // Refresh table
                productsTableModel.addRow(new Object[]{id, name, price, stock, supplier.getName()});
                
                JOptionPane.showMessageDialog(this, "Product added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid number format!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showAddRetailerDialog() {
        JTextField idField = new JTextField(10);
        JTextField nameField = new JTextField(20);
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Retailer ID:"));
        panel.add(idField);
        panel.add(new JLabel("Retailer Name:"));
        panel.add(nameField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Retailer", 
                                                  JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                
                IRetailer retailer = new Retailer(id, name);
                retailers.add(retailer);
                
                // Refresh table
                retailersTableModel.addRow(new Object[]{id, name});
                
                JOptionPane.showMessageDialog(this, "Retailer added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid ID format!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showPlaceOrderDialog() {
        // Check if retailers exist
        if (retailers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add a retailer first!", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get all products from all suppliers
        List<IProduct> allProducts = new ArrayList<>();
        List<ISupplier> suppliers = systemManager.getSuppliers();
        
        if (suppliers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add suppliers and products first!", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        for (ISupplier supplier : suppliers) {
            allProducts.addAll(supplier.getProducts());
        }
        
        if (allProducts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add products first!", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Prepare drop-downs
        String[] retailerNames = new String[retailers.size()];
        for (int i = 0; i < retailers.size(); i++) {
            retailerNames[i] = retailers.get(i).getRetailerID() + " - " + retailers.get(i).getName();
        }
        
        String[] productNames = new String[allProducts.size()];
        for (int i = 0; i < allProducts.size(); i++) {
            productNames[i] = allProducts.get(i).getProductID() + " - " + allProducts.get(i).getName();
        }
        
        JComboBox<String> retailerComboBox = new JComboBox<>(retailerNames);
        JComboBox<String> productComboBox = new JComboBox<>(productNames);
        JTextField quantityField = new JTextField(10);
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Retailer:"));
        panel.add(retailerComboBox);
        panel.add(new JLabel("Product:"));
        panel.add(productComboBox);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Place Order", 
                                                  JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                
                int selectedRetailerIndex = retailerComboBox.getSelectedIndex();
                IRetailer retailer = retailers.get(selectedRetailerIndex);
                
                int selectedProductIndex = productComboBox.getSelectedIndex();
                IProduct product = allProducts.get(selectedProductIndex);
                
                // Generate order ID
                int orderId = 1;
                for (IRetailer r : retailers) {
                    orderId += r.getOrders().size();
                }
                
                IOrder order = new Order(orderId, retailer.getRetailerID(), product.getProductID(), quantity);
                retailer.placeOrder(order);
                
                // Refresh orders table
                refreshOrdersTable();
                
                JOptionPane.showMessageDialog(this, "Order placed successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid quantity format!", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void processOrders() {
        for (IRetailer retailer : retailers) {
            for (IOrder order : retailer.getOrders()) {
                systemManager.processOrder(order);
            }
        }
    }
    
    private void refreshOrdersTable() {
        // Clear table
        ordersTableModel.setRowCount(0);
        
        // Refill with current data
        for (IRetailer retailer : retailers) {
            for (IOrder order : retailer.getOrders()) {
                ordersTableModel.addRow(new Object[]{
                    order.getOrderID(),
                    retailer.getName(),
                    order.getProductID(),
                    order.getQuantity(),
                    order.getStatus()
                });
            }
        }
    }
    
    // Add this method to refresh the products table
    private void refreshProductsTable() {
        // Clear table
        productsTableModel.setRowCount(0);
        
        // Refill with up-to-date data
        List<ISupplier> suppliers = systemManager.getSuppliers();
        for (ISupplier supplier : suppliers) {
            for (IProduct product : supplier.getProducts()) {
                productsTableModel.addRow(new Object[]{
                    product.getProductID(),
                    product.getName(),
                    product.getPrice(),
                    product.getStockQuantity(),  // This will now show the updated stock
                    supplier.getName()
                });
            }
        }
    }
    
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SupplyChainManagementSystemUI();
            }
        });
    }
}