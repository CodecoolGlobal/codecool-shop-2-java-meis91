package com.codecool.shop.controller;

import com.codecool.shop.config.DependencyResolver;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementationMem.CartDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.OrderStatus;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.CartService;
import com.codecool.shop.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet(urlPatterns = {"/payment/order-confirmation"})
public class ConfirmationController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ConfirmationController.class);
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

            if (Objects.equals(cartService.getCustomer().getShippingAddress().getAddress(), "")
                    && Objects.equals(cartService.getCustomer().getBillingAddress().getAddress(), "")
                    || cartService.getCart().isEmpty()) {
                logger.info("No Address / Cart Items");
                throw new NullPointerException("No Address / Cart Items");
            }
            engine.process("product/confirmation.html", context, resp.getWriter());
            //TODO Kathi: send confirmation email
            //TODO Kathi: save order into the Database
            System.out.println("cartService.getCart() = " + cartService.getCart());
            String totalPriceString = cartService.getTaxedTotalPrice();
            String totalPrice = totalPriceString.substring(0, totalPriceString.indexOf(" "));
            System.out.println("totalPrice = " + totalPrice);
            BigDecimal totalCost = BigDecimal.valueOf(Double.valueOf(totalPrice));
            System.out.println("totalCost = " + totalCost);
            List<Product> cart = cartService.getCart();

            Map<Integer, Integer> orderItems = new HashMap<>();
            for (Product product : cart) {
                int productId = product.getId();
                if (orderItems.containsKey(productId)) {
                    orderItems.put(productId, orderItems.get(productId)+1);
                } else {
                    orderItems.put(productId, 1);
                }
            }
            System.out.println("orderItems = " + orderItems);
            Order order = new Order(cartService.getCustomer(),
                    Date.valueOf(LocalDate.now()),
                    totalCost,
                    OrderStatus.CONFIRMED,
                    orderItems);
            OrderDao orderDao = DependencyResolver.MY_DEPENDENCIES.getImplementation(OrderDao.class);
            OrderService orderService = new OrderService(orderDao);
            orderService.add(order);

            cartService.removeAllItems();
        } catch (NullPointerException | IOException e) {
            context.setVariable("error", e.getMessage());
            engine.process("product/error.html", context, resp.getWriter());
        }

    }
}
