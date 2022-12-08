package com.codecool.shop.service;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.math.BigDecimal;
import java.util.List;

public class CartService {
    private int totalPrice;
    private CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public List<Product> getCart(){
        return cartDao.getAll();
    }

    public String getTotalPrice() {
        return cartDao.getTotalPrice();
    }
    public String getTax() {
        return cartDao.getTax();
    }
    public String getTaxedTotalPrice() {
        return cartDao.getTaxedTotalPrice();
    }

    public Customer getCustomer() {
        return cartDao.getCustomer();
    }

    public void setCustomer(Customer customer) {}
}
