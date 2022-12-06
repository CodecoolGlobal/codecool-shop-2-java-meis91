package com.codecool.shop.service;

import com.codecool.shop.dao.CartDao;
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

    public BigDecimal getTotalPrice() {
        return cartDao.getTotalPrice();
    }

}
