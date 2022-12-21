package com.codecool.shop.dao.implementationMem;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerDaoMem implements CustomerDao {

    private List<Customer> data = new ArrayList<>();
    private static CustomerDaoMem instance = null;

    public CustomerDaoMem() {

    }
    public static CustomerDaoMem getInstance() {
        if (instance == null) {
            instance = new CustomerDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Customer customer) {
        customer.setId(data.size()+1);
        data.add(customer);
    }

    @Override
    public Customer find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Customer> getAll() {
        return data;
    }

    public Customer getRegisteredUser(String email) {
        return null;
    }
}
