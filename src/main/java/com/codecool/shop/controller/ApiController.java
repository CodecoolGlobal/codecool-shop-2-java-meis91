package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementationJDBC.DatabaseManager;
import com.codecool.shop.dao.implementationJDBC.ProductDaoJdbc;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ProductService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.sql.DataSource;

import static java.lang.System.out;

@WebServlet("/api")
public class ApiController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = resp.getOutputStream();
        DatabaseManager databaseManager = new DatabaseManager();
        try {
            DataSource dataSource = databaseManager.connect();
            ProductDao productDataStore = new ProductDaoJdbc(dataSource);
            ProductService productService = new ProductService(productDataStore);
            if (req.getQueryString() != null) {
                if (req.getQueryString().contains("category")) {
                    int categoryId = Integer.parseInt(req.getParameter("category"));
                    List<Product> products = productService.getProductsForCategory(categoryId);
                    String output = new Gson().toJson(products);
                    out.print(output);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
