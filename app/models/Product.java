package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Product {

    private int id;
    private String name;
    private String description;
    private float price;
    private String version;
    private int amount;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    private int categoryId;
//    private Category category;
    private Publisher publisher;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFormattedAmount() {
        return "$" + amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public static List<Product> findAllAvailable(Statement stmt) throws SQLException {

        List<Product> products = new ArrayList<Product>();
        ResultSet rs = stmt.executeQuery("SELECT product_id,product_name,stock_amount,unit_price FROM product");

        // Fetch each row from the result set
        while (rs.next())
        {
            Product product = new Product();
            int amount = rs.getInt("stock_amount");
            if (amount > 0)
                product.setAmount(amount);
            else
                continue;
            product.setId(rs.getInt("product_id"));
            product.setName(rs.getString("product_name"));
            product.setPrice(rs.getFloat("unit_price"));
            products.add(product);
        }
        return products;
    }
    
    public  void setSpecific(Statement stmt,Long id) throws SQLException {

        //List<Product> products = new ArrayList<Product>();
        ResultSet rs = stmt.executeQuery("SELECT * FROM product where product_id = " + id);
        //Product product = new Product();
        // Fetch each row from the result set
        while (rs.next())
        {
           
            
            setId(rs.getInt("product_id"));
            setName(rs.getString("product_name"));
            setPrice(rs.getFloat("unit_price"));
            setDescription(rs.getString("product_description"));
            setAmount(rs.getInt("stock_amount")); 
            
            
            
        }
      
    }

}
