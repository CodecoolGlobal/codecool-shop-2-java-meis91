package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementationJDBC.DatabaseManager;
import com.codecool.shop.dao.implementationJDBC.ProductDaoJdbc;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

@WebServlet(name = "JsonController", urlPatterns = {"/json/*"}, loadOnStartup =0)
public class JsonController extends HttpServlet {
    //private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //ServletOutputStream out = resp.getOutputStream();
        //out.println(data);
        //
        try {
            DatabaseManager databaseManager = new DatabaseManager();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            DataSource dataSource = databaseManager.connect();
            ProductDao productDataStore = new ProductDaoJdbc(dataSource);
            ProductService productService = new ProductService(productDataStore);
            if (req.getQueryString() != null) {
                if (req.getQueryString().contains("category")) {
                    int categoryId = Integer.parseInt(req.getParameter("id"));
                    List<Product> products = productService.getProductsForCategory(categoryId);
                    products.stream().forEach(System.out::println);
                    System.out.println(products.size());
                    // TODO: 25.12.22 Fix the Problem with circular references, becuase Gson doesn`t likes them ;) 
                    Type listType = new TypeToken<List<Product>>(){}.getType();
                    System.out.println(listType);
                    Gson gson = new Gson();
                    //Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String output = gson.toJson(products, listType);
                    System.out.println(output);
                    out.print(output);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
