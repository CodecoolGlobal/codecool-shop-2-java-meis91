package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementationJDBC.DatabaseManager;
import com.codecool.shop.dao.implementationJDBC.ProductDaoJdbc;
import com.codecool.shop.dao.implementationMem.CartDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.CartItem;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.CartService;
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

@WebServlet(name = "JsonController", urlPatterns = {"/json/*", "shopping-cart/json/*"}, loadOnStartup =0)
public class JsonController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DatabaseManager databaseManager = new DatabaseManager();
            Gson gson = new Gson();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            DataSource dataSource = databaseManager.connect();
            ProductDao productDataStore = new ProductDaoJdbc(dataSource);
            ProductService productService = new ProductService(productDataStore);
            List<Product> products = new ArrayList<>();
            if (req.getQueryString() != null) {
                if (req.getQueryString().contains("category")) {
                    int categoryId = Integer.parseInt(req.getParameter("id"));
                    products = productService.getProductsForCategory(categoryId);
                } else if (req.getQueryString().contains("supplier")) {
                    int supplierId = Integer.parseInt(req.getParameter("id"));
                    products = productService.getProductsForSupplier(supplierId);
                } else if (req.getQueryString().contains("cart")) {
                    Type newsListType = new TypeToken<ArrayList<CartItem>>(){}.getType();
                    List<CartItem> cartItems = gson.fromJson( req.getParameter("cart"), newsListType);
                    Cart cart = new Cart(cartItems);
                    for (CartItem cartItem : cartItems) {
                        cartItem.getId();
                        products.add(productDataStore.find(cartItem.getId()));
                    }

                } else {
                    /*CartDao cartData = CartDaoMem.getInstance();
                    CartService cartService = new CartService(cartData);*/
                    int productId = Integer.parseInt(req.getParameter("id"));
                    products.add(productDataStore.find(productId));
                    //products = cartService.getCart();
                }

                String output = gson.toJson(products);
                out.print(output);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getHeaderNames().toString());
        System.out.println(req.getParameter("id"));
        System.out.println("got the post req");

    }
}
