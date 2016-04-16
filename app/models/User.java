package models;
import java.sql.*;

import javax.sql.DataSource;

import play.db.DB;




public class User {
	public String Username;
	public String Password;
	public String EmployeeName;
	
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
    
    public void set_EmployeeName(String empname){
    	
    	String first = "SELECT first_name, last_name FROM employee where user_name ='" + empname + "'";
    	String realName = null;
    	
         try {
             DataSource dsource = DB.getDataSource();
             Connection connecion = dsource.getConnection();
             Statement state = connecion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
             ResultSet welcomer = state.executeQuery(first);
            	while (welcomer.next()){
            		
            			realName = welcomer.getString("first_name") + " " + welcomer.getString("last_name");
            		
            	}
            	
            		
            	
            connecion.close();
         } catch (SQLException e) {
             e.printStackTrace();
         	}

         
         this.EmployeeName = realName;
    }

}
