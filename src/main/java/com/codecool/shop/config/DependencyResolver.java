package com.codecool.shop.config;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementationJDBC.*;
import com.codecool.shop.model.Cart;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DependencyResolver {
    public static final DependencyResolver MY_DEPENDENCIES = new DependencyResolver();

    private final Map<Class, Object> implementations;

    private DependencyResolver() {
        this.implementations = new HashMap<>();
        DatabaseManager databaseManager = new DatabaseManager();
        DataSource dataSource = null;
        try {
            dataSource = databaseManager.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.implementations.put(CartDao.class, new CartDaoJdbc(dataSource));
        this.implementations.put(CustomerDao.class, new CustomerDaoJdbc(dataSource));
        this.implementations.put(ProductCategoryDao.class, new ProductCategoryDaoJdbc(dataSource));
        this.implementations.put(ProductDao.class, new ProductDaoJdbc(dataSource));
        this.implementations.put(SupplierDao.class, new SupplierDaoJdbc(dataSource));
    }

    public <T> T getImplementation(Class<T> myInterface) {
        return (T) this.implementations.get(myInterface);
    }
}
