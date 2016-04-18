package controllers;

import java.sql.*;

import views.html.*;
import models.User;
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

    public static Result login() {

        DynamicForm userForm = Form.form().bindFromRequest();
        String username = userForm.data().get("username");
        String password = userForm.data().get("password");

        if (username == "") {
            flash("error", "Missing Username");
            return redirect(routes.Application.index());
        } else if (password == "") {
            flash("error", "Missing Password");
            return redirect(routes.Application.index());
        }
        boolean resultOK = false;

        try {
            DataSource ds = DB.getDataSource();
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultOK = User.isLogin(statement, username, password);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!resultOK) {
            flash("error", "Incorrect username or password.");
            return redirect(routes.Application.index());
        }
            User example = new User();
            example.set_EmployeeName(username);
            return ok(views.html.submit.render(example));
        }
    }
