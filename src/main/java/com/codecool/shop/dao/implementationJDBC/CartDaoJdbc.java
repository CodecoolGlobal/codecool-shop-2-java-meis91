package com.codecool.shop.dao.implementationJDBC;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class CartDaoJdbc implements CartDao {

    DataSource dataSource;

    public CartDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void add(Product product) {

    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {
    }

    @Override
    public void removeAll() {
    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }

    @Override
    public String getTotalPrice() {
        return null;
    }

    @Override
    public String getTax() {
        return null;
    }

    @Override
    public String getTaxedTotalPrice() {
        return null;
    }

    @Override
    public Customer getCustomer() {
        return null;
    }

    @Override
    public void setCustomer(Customer customer) {

    }
}
