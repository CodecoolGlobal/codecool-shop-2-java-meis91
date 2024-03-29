package com.codecool.shop.config;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementationMem.CartDaoMem;
import com.codecool.shop.dao.implementationMem.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementationMem.ProductDaoMem;
import com.codecool.shop.dao.implementationMem.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        CartDao cartDataStore = CartDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier(1, "Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier(2, "Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier dell = new Supplier(3, "Dell", "Computers");
        supplierDataStore.add(dell);
        Supplier mac = new Supplier(4, "Mac", "Computers");
        supplierDataStore.add(mac);
        //setting up a new product category
        ProductCategory tablet = new ProductCategory(1,"Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory laptop = new ProductCategory(2, "Laptop", "Hardware", "A foldable computer, with keyboard.");
        productCategoryDataStore.add(laptop);
        ProductCategory pc = new ProductCategory(3, "PC", "Hardware", "A classic computer, with keyboard.");
        productCategoryDataStore.add(pc);

        //setting up products and printing it
        productDataStore.add(new Product(1, "Amazon Fire", new BigDecimal("49.9"), "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product(2, "Lenovo IdeaPad Miix 700", new BigDecimal("479"), "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product(3, "Amazon Fire HD 8", new BigDecimal("89"), "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product(4, "Dell XPS 13", new BigDecimal("1298"), "USD", "Best of everything", laptop, dell));
        productDataStore.add(new Product(5, "Mac Pro", new BigDecimal("6499"), "USD", "Looks like a parmesan grater", pc, mac));
    }
}
