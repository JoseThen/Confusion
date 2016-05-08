package models;
import java.sql.*;

import javax.sql.DataSource;

import play.db.DB;


public class User {
	private String Username;
	private String Password;
    private String EmployeeName;
    private String Street, City, State, Email, Phone;
    private int Zip;
    private Date Dob;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setStreet(String street){
    	this.Street = street;
    }

    public void setCity(String city){
    	this.City = city;
    }

    public void setState(String state){
    	this.State = state;
    }

    public void setEmail(String email){
    	this.Email = email;
    }

    public void setPhone(String phone){
    	this.Phone = phone;
    }

    public void setZip(int zip){
    	this.Zip = zip;
    }

    public void setDob(Date dob){
    	this.Dob = dob;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }
	
	public void setUsername(String username) {
        this.Username = username;
    }

    public String getUsername() {
        return Username;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getPassword() {
        return Password;
    }

    //check if user with this username and password exists in our database
    public static boolean isLogin(Statement st, String userName, String password) throws SQLException {
        String query = String.format(
                "SELECT count(*) FROM employee WHERE " +
                        "user_name=\"%s\" AND " +
                        "pass_word=\"%s\"",
                userName,
                password
        );
        ResultSet set = st.executeQuery(query);
        if (set.next()) {
            return set.getInt(1) > 0;
        }
        return false;
    }
    
 // this method sets all of the variables pertaining to this class except for password
    public void set_EmployeeClass(Statement st, String empname)throws SQLException{

    			String first = "SELECT * FROM employee e,role_table r where e.role_id = r.role_id and user_name ='" + empname + "'";
    			String realName = null;
    			String street = null;
    			String city = null;
    			int zip = 0;
    			String state = null;
    			String email = null;
    			String phone = null;
    			Date dob = null;
                String title = "";


    			ResultSet welcomer = st.executeQuery(first);
            	while (welcomer.next()){
            		realName = welcomer.getString("first_name") + " " + welcomer.getString("last_name");
            		street = welcomer.getString("street");
            		city = welcomer.getString("city");
            		zip = welcomer.getInt("zip");
            		state = welcomer.getString("state");
            		email = welcomer.getString("email");
            		phone = welcomer.getString("phone");
            		dob = welcomer.getDate("dob");
                    title = welcomer.getString("role_title");
            		}
                    setUsername(empname);
            		setEmployeeName(realName);
            		setStreet(street);
            		setCity(city);
            		setZip(zip);
            		setState(state);
            		setEmail(email);
            		setPhone(phone);
            		setDob(dob);
                    setRole(title);
}

}
