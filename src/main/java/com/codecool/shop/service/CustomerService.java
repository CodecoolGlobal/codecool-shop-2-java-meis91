package com.codecool.shop.service;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Address;
import com.codecool.shop.model.Customer;

public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {this.customerDao = customerDao;}

    public Customer getCustomerByEMail(String eMail) {
        return customerDao.getRegisteredUser(eMail);
    }

    public void add(Customer customer) {
        customerDao.add(customer);
    }
}
