package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.CustomerDaoMem;
import com.codecool.shop.model.Address;
import com.codecool.shop.model.AddressType;
import com.codecool.shop.model.Customer;
import com.codecool.shop.service.CartService;
import com.codecool.shop.service.CustomerService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDao cartData = CartDaoMem.getInstance();
        CartService cartService = new CartService(cartData);
        CustomerDao customerData = CustomerDaoMem.getInstance();
        CustomerService customerService = new CustomerService(customerData);

        Customer currentCustomer = new Customer(
                req.getParameter("modal-name"),
                req.getParameter("modal-email"),
                req.getParameter("modal-phone-number"),
                new Address(
                        req.getParameter("modal-country-billing"),
                        req.getParameter("modal-city-billing"),
                        req.getParameter("modal-zipcode-billing"),
                        req.getParameter("modal-address-billing"),
                        AddressType.BILLING
                ),
                new Address(
                        req.getParameter("modal-country-shipping"),
                        req.getParameter("modal-city-shipping"),
                        req.getParameter("modal-zipcode-shipping"),
                        req.getParameter("modal-address-shipping"),
                        AddressType.SHIPPING
                )
        );

        customerData.add(currentCustomer);
        cartData.setCustomer(currentCustomer);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("total_price", cartService.getTotalPrice());
        engine.process("product/payment.html", context, resp.getWriter());
    }

}