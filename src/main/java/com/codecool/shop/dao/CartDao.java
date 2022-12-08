package com.codecool.shop.dao;

import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.math.BigDecimal;
import java.util.List;

public interface CartDao {

    void add(Product product);
    Product find(int id);
    void remove(int id);

    List<Product> getAll();
    List<Product> getBy(Supplier supplier);
    List<Product> getBy(ProductCategory productCategory);

    String getTotalPrice();
    String getTax();
    String getTaxedTotalPrice();

    Customer getCustomer();
    void setCustomer(Customer customer);
}
