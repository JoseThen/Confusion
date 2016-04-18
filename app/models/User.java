package models;
import java.sql.*;

import javax.sql.DataSource;

import play.db.DB;


public class User {
	private String Username;
	private String Password;
    private String EmployeeName;

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
