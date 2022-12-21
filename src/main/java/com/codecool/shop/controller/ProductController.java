package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementationJDBC.DatabaseManager;
import com.codecool.shop.dao.implementationJDBC.ProductDaoJdbc;
import com.codecool.shop.dao.implementationMem.CartDaoMem;
import com.codecool.shop.dao.implementationMem.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementationMem.ProductDaoMem;
import com.codecool.shop.dao.implementationMem.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
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
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DatabaseManager databaseManager = new DatabaseManager();
        try {
            DataSource dataSource = databaseManager.connect();
            ProductDao productDataStore = new ProductDaoJdbc(dataSource);
            ProductService productService = new ProductService(productDataStore);
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            WebContext context = new WebContext(req, resp, req.getServletContext());
            context.setVariable("allProducts", productDataStore.getAll());

            context.setVariable("allCategories", productService.getAllCategories());
            context.setVariable("allSupplier", productService.getAllSupplier());
            engine.process("product/products.html", context, resp.getWriter());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        /*ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        CartDao cartsOfUser = CartDaoMem.getInstance();
        ProductService productService = new ProductService(productDataStore,productCategoryDataStore, productSupplierDataStore);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        if (req.getQueryString() != null) {
            if (req.getQueryString().contains("category")) {
                categoryId = Integer.parseInt(req.getParameter("category"));
                context.setVariable("category", productService.getProductCategory(categoryId));
                context.setVariable("products", productService.getProductsForCategory(categoryId));
            } else if (req.getQueryString().contains("supplier")) {
                supplierId = Integer.parseInt(req.getParameter("supplier"));
                context.setVariable("category", productService.getProductSupplier(supplierId));  //I cheated by calling the Variable category and not supplier
                context.setVariable("products", productService.getProductsForSupplier(supplierId));
            }
            else{
                boolean test = true;
                for (Product product: cartsOfUser.getAll()) {
                    if(product.getId() == Integer.parseInt(req.getParameter("cart"))){
                        product.setCountOfProduct(product.getCountOfProduct() + 1 );
                        logger.info("Added one more product to cart: {}", product);
                        test = false;
                    }
                }
                if(test){
                    cartsOfUser.add(productDataStore.find(Integer.parseInt(req.getParameter("cart"))));
                }
                context.setVariable("cartUser", cartsOfUser.getAll());
                context.setVariable("category", productService.getAllCategories());
            }

        } else {
                context.setVariable("category", productService.getAllCategories());
        }
        context.setVariable("allCategories", productService.getAllCategories());
        context.setVariable("allSupplier", productService.getAllSupplier());
        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/index.html", context, resp.getWriter());*/
    }

}
