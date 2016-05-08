package models;

import controllers.Application;

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
    private String platform;
    private int amount;
    private Category category;
    private Publisher publisher;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

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

    public static void delete(Statement stmt, Long productId) throws SQLException {
        stmt.executeUpdate("DELETE FROM product WHERE product_id =" + productId);
    }

    public static List<Product> findAllAvailable(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT product_id,product_name,stock_amount,unit_price FROM product");
        return getListOfProducts(rs);
    }

    public static List<Product> findByName(Statement stmt, String name) throws SQLException {
        String query = "SELECT product_id,product_name,stock_amount,unit_price FROM product WHERE " +
                        "product_name LIKE '%" + name + "%'";

        ResultSet rs = stmt.executeQuery(query);
        return getListOfProducts(rs);
    }
    
    public  void setSpecific(Statement stmt,Long id) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM product p,category c where c.category_id = p.category_id and product_id = " + id);
        // Fetch each row from the result set
        while (rs.next())
        {
            setId(rs.getInt("product_id"));
            setName(rs.getString("product_name"));
            setPrice(rs.getFloat("unit_price"));
            setDescription(rs.getString("product_description"));
            setAmount(rs.getInt("stock_amount"));
            setPlatform(rs.getString("platform"));
            Category c = new Category();
            c.setId(rs.getInt("category_id"));
            c.setName(rs.getString("category_name"));
            setCategory(c);
            Publisher pub = new Publisher();
            pub.findPublisher(Application.getDBConnection(),(long)rs.getInt("publisher_id"));
            setPublisher(pub);
        }
      
    }

    public static List<Product> sortByName(Statement stmt, String order) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT product_id,product_name,stock_amount,unit_price FROM product ORDER BY product_name " + order);
        return getListOfProducts(rs);
    }

    public static List<Product> sortByPrice(Statement stmt, String order) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT product_id,product_name,stock_amount,unit_price FROM product ORDER BY unit_price " + order);
        return getListOfProducts(rs);
    }

    public static List<Product> findByCategory(Statement stmt, Long categoryId) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT product_id,product_name,stock_amount,unit_price FROM product p,category c "+
                "WHERE c.category_id = p.category_id AND c.category_id = "+ categoryId);
//        String q = "SELECT product_id,product_name,stock_amount,unit_price FROM product,category WHERE category.category_id = product.category_id AND category.category_id = " + categoryId;
//        ResultSet rs = stmt.executeQuery(q);
        return getListOfProducts(rs);
    }

    public static List<Product> findByPublisher(Statement stmt, Long publisherId) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT product_id,product_name,stock_amount,unit_price FROM product,publisher "+
                "WHERE publisher.publisher_id = product.publisher_id AND publisher.publisher_id = "+ publisherId);
        return getListOfProducts(rs);
    }

    public static List<Product> getListOfProducts(ResultSet rs) throws SQLException {
        List<Product> products = new ArrayList<Product>();
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
}
