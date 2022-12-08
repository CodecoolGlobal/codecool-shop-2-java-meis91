package com.codecool.shop.service;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Address;
import com.codecool.shop.model.Customer;

public class CustomerService {
    private CustomerDao customerDao;
    Address shippingAddress;
    Address billingAddress;

    public CustomerService(CustomerDao customerDao) {this.customerDao = customerDao;}


}
