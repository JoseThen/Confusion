package controllers;
import java.util.HashMap;
import java.util.Map;

import  models.User; 
import play.mvc.Controller;
import play.mvc.Http.Request;
import play.mvc.Result;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

public class Application extends Controller {
    
	
	
    public static Result index() {
        return ok(views.html.index.render("Confusion Inventory"));
    }
    
    public static Result submit() {
    	
    	User example = new User();
    	
    	
    	 DynamicForm userForm = Form.form().bindFromRequest();
         String username = userForm.data().get("username");
         String password = userForm.data().get("password");
         
         example.setUsername(username);
         example.setPassword(password);
         
         
//         Map<String, String> credentials = new HashMap<String, String>();
//
// 	    credentials.put("USER", username );
// 	    credentials.put("PASS", password);
 	    
 	   

    	if (username == "" ){
    		 flash("error", "Missing Username");
             return ok(views.html.index.render("Enter a Username!"));
    	}
    	else if (password == "" ){
    		flash("error", "Missing Password");
            return ok(views.html.index.render("Enter a Password!"));
    	}
    	
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

  
}
