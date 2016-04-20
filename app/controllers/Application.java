package controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Product;
import views.html.*;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

import play.data.DynamicForm;
import play.data.Form;
import play.db.*;


import javax.sql.DataSource;

public class Application extends Controller {

    public static Connection connection;

    public static Result index() {
        return ok(views.html.index.render("Confusion Inventory"));
    }

    public static Result login() throws SQLException {

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

            resultOK = User.isLogin(getDBConnection(), username, password);
            connection.close();

        if (!resultOK) {
            flash("error", "Incorrect username or password.");
            return redirect(routes.Application.index());
        }
            User example = new User();
            example.set_EmployeeName(username);
            List <Product> products = Product.findAllAvailable(getDBConnection());
            connection.close();
            return ok(views.html.inventory.render(example,products));
        }

    //future product description page
    public static Result show(Long id) {
        return ok(views.html.index.render("hey"));
    }

    public static Statement getDBConnection() throws SQLException {
        DataSource ds = DB.getDataSource();
        connection = ds.getConnection();
        return connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

    }

    }
