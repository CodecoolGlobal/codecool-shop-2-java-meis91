package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementationMem.CartDaoMem;
import com.codecool.shop.model.Address;
import com.codecool.shop.model.AddressType;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.CartService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/shopping-cart/"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDao cartData = CartDaoMem.getInstance();
        CartService cartService = new CartService(cartData);
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        if (req.getQueryString() != null) {
            if (req.getQueryString().contains("remove")) {
                Product delete = null;
                for (Product p: cartData.getAll() ) {
                    if(p.getId() == Integer.parseInt(req.getParameter("remove"))){
                        if(p.getCountOfProduct() != 1){
                            p.setCountOfProduct(p.getCountOfProduct() - 1);
                        }
                        else{
                            delete = p;
                        }
                    }
                }
                cartData.getAll().remove(delete);
            }
            if (req.getQueryString().contains("add")) {
                if (req.getQueryString().contains("add")) {
                    for (Product p: cartData.getAll() ) {
                        if(p.getId() == Integer.parseInt(req.getParameter("add"))){
                                p.setCountOfProduct(p.getCountOfProduct() + 1);
                        }
                    }
                }
            }
        }
        context.setVariable("products", cartService.getCart());
        context.setVariable("total_price", cartService.getTotalPrice());
        resp.getWriter().println("<!-- -->");
        engine.process("product/cart.html", context, resp.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Address billingAddress;
        Address shippingAddress;
        Customer customer;

        String name = req.getParameter("modal-name");
        String email = req.getParameter("modal-email");
        String phoneNumber = req.getParameter("modal-phone-number");

        String billingCountry = req.getParameter("modal-country-billing");
        String billingCity = req.getParameter("modal-city-billing");
        String billingZipCode = req.getParameter("modal-zipcode-billing");
        String billingAdd = req.getParameter("modal-address-billing");

        String shippingCountry = req.getParameter("modal-country-shipping");
        String shippingCity = req.getParameter("modal-city-shipping");
        String shippingZipCode = req.getParameter("modal-zipcode-shipping");
        String shippingAdd = req.getParameter("modal-address-shipping");

        billingAddress = new Address(billingCountry, billingCity, billingZipCode, billingAdd, AddressType.BILLING);
        shippingAddress = new Address(shippingCountry, shippingCity, shippingZipCode, shippingAdd, AddressType.SHIPPING);
        customer = new Customer(name, email, phoneNumber, billingAddress, shippingAddress);
        System.out.println(name);
        PaymentController paymentController = new PaymentController();
        paymentController.doGet(req, resp);

    }
}
