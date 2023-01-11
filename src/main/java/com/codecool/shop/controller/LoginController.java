package com.codecool.shop.controller;

import com.codecool.shop.config.DependencyResolver;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.implementationJDBC.CustomerDaoJdbc;
import com.codecool.shop.dao.implementationJDBC.DatabaseManager;
import com.codecool.shop.model.Customer;
import com.codecool.shop.service.CustomerService;
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

    String errorMessage = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("errorMessage", errorMessage);
        engine.process("customer/login.html", context, resp.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer;

        String email = req.getParameter("email");
        String password = req.getParameter("password");


        CustomerDao customerDao = DependencyResolver.MY_DEPENDENCIES.getImplementation(CustomerDao.class);
        CustomerService customerService = new CustomerService(customerDao);
        customer = customerService.getCustomerByEMail(email);

        String hashedUserPassword;
        String hashedInputPassword;

        try {
            hashedUserPassword = customer.getHashedPassword();
            byte[] salt = customer.getSalt();
            hashedInputPassword = PasswordSecurity.getSecurePassword(password, salt);
            if (hashedInputPassword.equals(hashedUserPassword)) {
                HttpSession session = req.getSession();
                session.setAttribute("email", customer.getEMail());
                errorMessage = null;
                resp.sendRedirect(req.getContextPath() + "/");
            }
            else {
                logger.warn("Wrong password");
                errorMessage = "Wrong password, please try again.";
                doGet(req, resp);
            }
        } catch (NullPointerException e) {
            logger.warn("Unknown email address - user is not registered");
            errorMessage = "Unknown email address, please try again or register.";
            doGet(req, resp);
        }
    }
}

