package com.codecool.shop.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {


    private Customer customer;
    private Map<Product, Integer> shoppingCart = new HashMap<>();

    public Cart() {

    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void addProductToCart(Product product){
        int amount = 1;
        if(shoppingCart.containsKey(product)){
            amount = shoppingCart.get(product) + 1;
            shoppingCart.put(product, amount);
        }
        shoppingCart.put(product, amount);
    }

    public Map<Product, Integer> getShoppingCart() {
        return shoppingCart;
    }
}
