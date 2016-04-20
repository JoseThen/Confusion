package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Publisher {


    private String name;
    private String country;


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Publisher> findAll(Statement stmt) throws SQLException {

        List<Publisher> publishers = new ArrayList<Publisher>();
        ResultSet rs = stmt.executeQuery("SELECT publisher_name FROM publisher");

        // Fetch each row from the result set
        while (rs.next())
        {
            Publisher p = new Publisher();
//            p.setId(rs.getInt("publisher_id"));
            p.setName(rs.getString("publisher_name"));
            publishers.add(p);
        }
        return publishers;
    }
}
