package controllers;
import java.sql.*;
import  models.User;
import play.mvc.Controller;
import play.mvc.Result;

import play.data.DynamicForm;
import play.data.Form;
import play.db.*;




import javax.sql.DataSource;

public class Application extends Controller {
	
    public static Result index() {
        return ok(views.html.index.render("Confusion Inventory"));
    }
    
    public static Result submit() {

        DynamicForm userForm = Form.form().bindFromRequest();
        String username = userForm.data().get("username");
        String password = userForm.data().get("password");

        if (username == "" ){
            flash("error", "Missing Username");
            return ok(views.html.index.render("Enter a Username!"));
        }
        else if (password == "" ){
            flash("error", "Missing Password");
            return ok(views.html.index.render("Enter a Password!"));
        }

        boolean resultOK = false;

        try {
            DataSource ds = DB.getDataSource();
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultOK = isLogin(statement, username, password);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!resultOK) {
            flash("error", "Incorrect username or password.");
            return ok(views.html.index.render("Incorrect username or password."));
        }

        User example = new User();
        example.setUsername(username);
        example.setPassword(password);
         
//         Map<String, String> credentials = new HashMap<String, String>();
//
// 	    credentials.put("USER", username );
// 	    credentials.put("PASS", password);
    	
        return ok(views.html.submit.render(example));
    }
    
    

    
    
//    public static Result myAction() {
//    String[] postAction = request().body().asFormUrlEncoded().get("action");
//    if (postAction == null || postAction.length == 0) {
//      return badRequest("You must provide a valid action");
//    }
//    else {
//      String action = postAction[0];
//      if ("submit".equals(action)) {
//        return edit(request());
//      } 
//      else {
//        return badRequest("Something Bad Happened :(");
//      }
//    }
//  }
//
//  private static Result edit(Request request) {
//    // TODO
//    return submit();
//  }


    private static boolean isLogin(Statement st, String userName, String password) throws SQLException {
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
  
}
