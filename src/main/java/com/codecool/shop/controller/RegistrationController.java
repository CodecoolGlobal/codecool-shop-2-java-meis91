package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.implementationJDBC.CustomerDaoJdbc;
import com.codecool.shop.dao.implementationJDBC.DatabaseManager;
import com.codecool.shop.dao.implementationMem.CartDaoMem;
import com.codecool.shop.model.Address;
import com.codecool.shop.model.AddressType;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.CartService;
import com.codecool.shop.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/registration/"})
public class RegistrationController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        engine.process("product/registration.html", context, resp.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer;

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        customer = new Customer(email, password, null, null, null);

        logger.info("Received new User Data: {}", customer);
        DatabaseManager databaseManager = new DatabaseManager();

        try {
            databaseManager.setup();
            DataSource dataSource = databaseManager.connect();
            CustomerDao customerDao = new CustomerDaoJdbc(dataSource);
            CustomerService customerService = new CustomerService(customerDao);
            customerDao.add(customer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ProductController productController = new ProductController();
        productController.doGet(req, resp);

    }
}

