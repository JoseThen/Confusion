package controllers;
import  models.User; 
import play.mvc.Controller;
import play.mvc.Http.Request;
import play.mvc.Result;
import play.mvc.*;

public class Application extends Controller {
    
	
	
    public static Result index() {
        return ok(views.html.index.render("Confusion"));
    }
    
    public static Result submit() {
    	return ok(views.html.submit.render("Balls"));
    	}
    public static Result myAction() {
    String[] postAction = request().body().asFormUrlEncoded().get("action");
    if (postAction == null || postAction.length == 0) {
      return badRequest("You must provide a valid action");
    }
    else {
      String action = postAction[0];
      if ("submit".equals(action)) {
        return edit(request());
      } 
      else {
        return badRequest("Something Bad Happened :(");
      }
    }
  }

  private static Result edit(Request request) {
    // TODO
    return submit();
  }

  
}
