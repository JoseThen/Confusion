package controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Category;
import models.Product;
import models.Publisher;
import notifiers.Mails;
import play.mvc.Content;
import play.mvc.Security;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

import play.data.DynamicForm;
import play.data.Form;
import play.db.*;


import javax.sql.DataSource;

public class Application extends Controller {

    public static Result index() {
        if (session().get("username") != null){
            session().remove("username");
        }
        return ok(views.html.index.render("GameGo Inventory"));
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

        Connection connection = getDBConnection();
        try {
            Statement statement = createStatement(connection);
            resultOK = User.isLogin(statement, username, password);
        } finally {
            connection.close();
        }


        if (!resultOK) {
            flash("error", "Incorrect username or password.");
            return redirect(routes.Application.index());
        }
        User user = new User();

        connection = getDBConnection();
        try {
            Statement statement = createStatement(connection);
            user.set_EmployeeClass(statement,username);
        } finally {
            connection.close();
        }

        session("username", user.getUsername());
        return redirect(routes.Application.showAll());
        }
    @Security.Authenticated(UserAuth.class)
    public static Result showAll() throws SQLException {
        Connection connection = getDBConnection();
        try {
            List <Product> products = Product.findAllAvailable(createStatement(connection));
            List <Publisher> publishers = Publisher.findAll(createStatement(connection));
            List <Category> categories = Category.findAll(createStatement(connection));
            return ok(views.html.inventory.render(products,publishers,categories));
        } finally {
            connection.close();
        }
    }

    public static Result logout(){
        session().remove("username");
        return redirect(routes.Application.index());
    }

    //future product description page
    @Security.Authenticated(UserAuth.class)
    public static Result show(long id) throws SQLException {
    	Publisher publicador = new Publisher();
        Product productos = new  Product();
        Connection connection = getDBConnection();
        try {
            productos.setSpecific(connection, id);
            publicador.findPublisher(createStatement(connection), (long)productos.getPublisher().getId());
        } finally {
            connection.close();
        }

        User user = actions.CurrentUser.currentUser();
        return ok(views.html.product.product.render(productos, publicador, user.getRole().equals("Manager")));
      }

    @Security.Authenticated(UserAuth.class)
    public static Result search() throws SQLException {
        String search = "";
        DynamicForm dataForm = Form.form().bindFromRequest();
        search = dataForm.data().get("search");

        Connection connection = getDBConnection();
        try {
            List <Product> products = Product.findByName(createStatement(connection),search);
            List <Publisher> publishers = Publisher.findAll(createStatement(connection));
            List <Category> categories = Category.findAll(createStatement(connection));
            return ok(views.html.inventory.render(products,publishers,categories));
        } finally {
            connection.close();
        }
    }

    @Security.Authenticated(UserAuth.class)
    public static Result sortByName(String order) throws SQLException {
        Connection connection = getDBConnection();
        try {
            List <Product> products = Product.sortByName(createStatement(connection), order);
            List <Publisher> publishers = Publisher.findAll(createStatement(connection));
            List <Category> categories = Category.findAll(createStatement(connection));
            return ok(views.html.inventory.render(products,publishers,categories));
        } finally {
            connection.close();
        }
    }

    @Security.Authenticated(UserAuth.class)
    public static Result sortByPrice(String order) throws SQLException {
        Connection connection = getDBConnection();
        try {
            List<Product> products = Product.sortByPrice(createStatement(connection), order);
            List<Publisher> publishers = Publisher.findAll(createStatement(connection));
            List<Category> categories = Category.findAll(createStatement(connection));
            return ok(views.html.inventory.render(products, publishers, categories));
        } finally {
            connection.close();
        }
    }



    @Security.Authenticated(UserAuth.class)
    public static Result findByPublisher(Long pubId) throws SQLException {
        Connection connection = getDBConnection();
        try {
            List <Product> products = Product.findByPublisher(createStatement(connection), pubId);
            List <Publisher> publishers = Publisher.findAll(createStatement(connection));
            List <Category> categories = Category.findAll(createStatement(connection));
            return ok(views.html.inventory.render(products,publishers,categories));
        } finally {
            connection.close();
        }
    }

    @Security.Authenticated(UserAuth.class)
    public static Result findByCategory(Long categoryId) throws SQLException {
        Connection connection = getDBConnection();
        try {
            List <Product> products = Product.findByCategory(createStatement(connection), categoryId);
            List <Publisher> publishers = Publisher.findAll(createStatement(connection));
            List <Category> categories = Category.findAll(createStatement(connection));
            return ok(views.html.inventory.render(products,publishers,categories));
        } finally {
            connection.close();
        }
    }

    @Security.Authenticated(UserAuth.class)
    public static Result delete(Long productId) throws SQLException {
        User user = actions.CurrentUser.currentUser();
        if (!user.getRole().equals("Manager")){
            flash("error", "You have no privileges to perform this action");
        } else {
            Connection connection = getDBConnection();
            try {
                Product.delete(createStatement(connection),productId);
            } finally {
                connection.close();
            }
        }
        return redirect(routes.Application.showAll());
    }
    @Security.Authenticated(UserAuth.class)
    public static Result edit(Long productId) throws SQLException {
        Connection connection = getDBConnection();
        try {
            Product product = new  Product();
            if (productId != null) {
                product.setSpecific(connection, productId);
            } else {
                product = null;
            }
            List <Publisher> publishers = Publisher.findAll(createStatement(connection));
            List <Category> categories = Category.findAll(createStatement(connection));
            return ok(views.html.product.edit.render(product, publishers, categories));
        } finally {
            connection.close();
        }
    }

    @Security.Authenticated(UserAuth.class)
    public static Result update(Long productId) throws SQLException {
        User user = actions.CurrentUser.currentUser();
        if (!user.getRole().equals("Manager")) {
            flash("error", "You have no privileges to perform this action");
        } else {
            Product product = new Product();
            if (productId != null) {
                Connection connection = getDBConnection();
                try {
                    product.setSpecific(connection, productId);
                } finally {
                    connection.close();
                }
            }
            DynamicForm dForm = Form.form().bindFromRequest();
            //change details and save.
            String name = dForm.data().get("name");
            String description = dForm.data().get("description");
            String price = dForm.data().get("price");
            int categoryId = Integer.parseInt(dForm.data().get("category"));
            int publisherId = Integer.parseInt(dForm.data().get("publisher"));
            if (name == "" || description == "" || price == "" || categoryId == 0 || publisherId == 0) {
                flash("error", "Required fields cannot be empty");
                return redirect(routes.Application.edit(productId));
            } else {
                float priceNumb = Float.parseFloat(dForm.data().get("price"));
                String platform = dForm.data().get("platform");
                int amount = Integer.parseInt(dForm.data().get("amount"));
                product.setName(name);
                product.setPrice(priceNumb);
                product.setDescription(description);
                product.setAmount(amount);
                product.setPlatform(platform);

                Connection connection = getDBConnection();
                try {
                    if(product.getId()!= null) {
                        product.update(createStatement(connection), categoryId, publisherId);
                    }else{
                        product.add(createStatement(connection), categoryId, publisherId);
                    }
                } finally {
                    connection.close();
                }
            }
        }
        return redirect(productId!= null ?routes.Application.show(productId) : routes.Application.showAll());
    }

    @Security.Authenticated(UserAuth.class)
    public static Result sendReport() throws SQLException {
        // Prepare report
        Content htmlReportContent;
        Connection connection = getDBConnection();
        try {
            List <Product> products = Product.findAllAvailable(createStatement(connection));
            htmlReportContent = views.html.reports.inventory.render(products);
        } finally {
            connection.close();
        }

        // Send email
        List<String> recipients = new ArrayList<String>();
        recipients.add("hey.november@gmail.com");
        Mails.sendEmail("Game Go", recipients, htmlReportContent.toString());
        flash("success", "The report has been successfully sent");
        return redirect(routes.Application.showAll());
//        return ok(htmlReportContent);
    }

    public static Connection getDBConnection() throws SQLException {
        DataSource ds = DB.getDataSource();
        return  ds.getConnection();
    }

    public static Statement createStatement(Connection connection)  throws SQLException {
        return connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    }
}