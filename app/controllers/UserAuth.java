package controllers;
import models.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.sql.SQLException;

public class UserAuth extends Security.Authenticator {

        //When return is null, that means authentication failed
        @Override
        public String getUsername(final Http.Context ctx){
            String username = ctx.session().get("username");
            return username;
        }

        @Override
        public Result onUnauthorized(final Http.Context ctx){
            ctx.flash().put("error",
                    "Your session has expired. Please, log in");
            return redirect(routes.Application.index());
        }
}

