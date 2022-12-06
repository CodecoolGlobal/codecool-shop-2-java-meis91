package com.codecool.shop.model;

import java.math.BigDecimal;
import java.util.List;

public class Cart {


    private List<Product> shoppingCart;
    private Customer customer;

    public Cart() {

    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}
