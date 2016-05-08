package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Publisher {


    private int id;
    private String name;
    private String country;
    private String yearStarted;
    private String pubDescription;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYearStarted() {
        return yearStarted;
    }

    public void setYearStarted(String yearStarted) {
        this.yearStarted = yearStarted;
    }

    public String getPubDescription() {
        return pubDescription;
    }

    public void setPubDescription(String pubDescription) {
        this.pubDescription = pubDescription;
    }

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
        ResultSet rs = stmt.executeQuery("SELECT publisher_name, publisher_id FROM publisher");

        // Fetch each row from the result set
        while (rs.next()) {
            Publisher p = new Publisher();
            p.setId(rs.getInt("publisher_id"));
            p.setName(rs.getString("publisher_name"));
            publishers.add(p);
        }
        return publishers;
    }

    public void findPublisher(Statement stmt, Long id) throws SQLException {

        ResultSet rs = stmt.executeQuery("SELECT * FROM publisher where publisher_id = " + id);
        // Fetch each row from the result set
        while (rs.next()) {
            setId(rs.getInt("publisher_id"));
            setPubDescription(rs.getString("publisher_description"));
            setYearStarted(rs.getString("establishing_year"));
            setName(rs.getString("publisher_name"));
            setCountry(rs.getString("country_of_origin"));
        }

    }
}