package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerDaoMem implements CustomerDao {

    private List<Customer> data = new ArrayList<>();
    private static CustomerDaoMem

    @Override
    public void add(Customer customer) {
        customer.setId(data.size()+1);
        data.add(customer);
    }


}
