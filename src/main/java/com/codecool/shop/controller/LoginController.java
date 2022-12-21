package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.implementationJDBC.CustomerDaoJdbc;
import com.codecool.shop.dao.implementationJDBC.DatabaseManager;
import com.codecool.shop.model.Customer;
import com.codecool.shop.util.PasswordSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/login/"})
public class LoginController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        engine.process("customer/login.html", context, resp.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer;

        String email = req.getParameter("email");
        String password = req.getParameter("password");

//        try {
//            String hashedPassword = PasswordSecurity.hashPassword(password);
//            customer = new Customer(email, hashedPassword, null, null, null);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//
//        logger.info("Received new User Data: {}", customer);
        DatabaseManager databaseManager = new DatabaseManager();

        try {
            DataSource dataSource = databaseManager.connect();
            CustomerDao customerDao = new CustomerDaoJdbc(dataSource);
            customer = customerDao.getRegisteredUser(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String hashedInputPassword;
        String hashedUserPassword = customer.getHashedPassword();
        byte[] salt = customer.getSalt();
        hashedInputPassword = PasswordSecurity.getSecurePassword(password, salt);

        if (hashedInputPassword.equals(hashedUserPassword)) {
            HttpSession session = req.getSession();
            session.setAttribute("email", customer.getEMail());
            resp.sendRedirect(req.getContextPath() + "/");
            System.out.println("passwords are equal");
        }
        else {
            System.out.println("Wrong password");
        }
        logger.info("I have got passwords! {} {}", hashedInputPassword, hashedUserPassword);
        ProductController productController = new ProductController();
        productController.doGet(req, resp);

    }
}

