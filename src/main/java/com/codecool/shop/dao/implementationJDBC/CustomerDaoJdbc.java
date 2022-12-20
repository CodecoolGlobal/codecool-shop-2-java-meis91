package com.codecool.shop.dao.implementationJDBC;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;

import javax.sql.DataSource;
import java.util.List;

public class CustomerDaoJdbc extends DaoJdbc implements CustomerDao {
    public CustomerDaoJdbc(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void add(Customer customer) {

    }

    @Override
    public Customer find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Customer> getAll() {
        return null;
    }
}
