package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementationMem.CartDaoMem;
import com.codecool.shop.service.CartService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet(urlPatterns = {"/payment/order-confirmation"})
public class ConfirmationController extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        CartDao cartData = CartDaoMem.getInstance();
        CartService cartService = new CartService(cartData);

        try {
            context.setVariable("customer", cartService.getCustomer());
            context.setVariable("cart", cartService.getCart());
            context.setVariable("tax", cartService.getTax());
            context.setVariable("taxedTotalPrice", cartService.getTaxedTotalPrice());

            if (Objects.equals(cartService.getCustomer().getShippingAddress().getAddress(), "") || cartService.getCart().isEmpty()) {
                throw new NullPointerException("No Address / Cart Items");
            }
            engine.process("product/confirmation.html", context, resp.getWriter());
        } catch (NullPointerException | IOException e) {
            context.setVariable("error", e.getMessage());
            engine.process("product/error.html", context, resp.getWriter());
        }
    }
}
