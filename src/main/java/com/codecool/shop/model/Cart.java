package com.codecool.shop.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    private Customer customer;
    private List<CartItem> shoppingCart;

    public Cart(List<CartItem> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CartItem> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<CartItem> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
