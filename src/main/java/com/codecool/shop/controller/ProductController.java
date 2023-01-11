package com.codecool.shop.controller;

import com.codecool.shop.config.DependencyResolver;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementationJDBC.DatabaseManager;
import com.codecool.shop.dao.implementationJDBC.ProductDaoJdbc;
import com.codecool.shop.dao.implementationMem.CartDaoMem;
import com.codecool.shop.dao.implementationMem.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementationMem.ProductDaoMem;
import com.codecool.shop.dao.implementationMem.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductController", urlPatterns = {"/", "/product"}, loadOnStartup = 1)
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = DependencyResolver.MY_DEPENDENCIES.getImplementation(ProductDao.class);
        ProductService productService = new ProductService(productDataStore);
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        List<Product> products = new ArrayList<>();
        if (req.getQueryString() != null) {
            if (req.getQueryString().contains("category")) {
                int categoryId = Integer.parseInt(req.getParameter("category"));
                products = productService.getProductsForCategory(categoryId);
            } else if (req.getQueryString().contains("supplier")) {
                int supplierId = Integer.parseInt(req.getParameter("supplier"));
                products = productService.getProductsForSupplier(supplierId);
            }
        } else {
            products = productService.getAllProducts();
        }
        context.setVariable("allProducts", products);
        context.setVariable("allCategories", productService.getAllCategories());
        context.setVariable("allSupplier", productService.getAllSupplier());
        engine.process("product/products.html", context, resp.getWriter());
    }
}
