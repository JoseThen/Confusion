package models;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Category {

    private int id;
    private String name;

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

    public static List<Category> findAll(Statement stmt) throws SQLException {

        List<Category> categories = new ArrayList<Category>();
        ResultSet rs = stmt.executeQuery("SELECT * FROM category");

        // Fetch each row from the result set
        while (rs.next())
        {
            Category c = new Category();
            c.setId(rs.getInt("category_id"));
            c.setName(rs.getString("category_name"));
            categories.add(c);
        }
        return categories;
    }

}
