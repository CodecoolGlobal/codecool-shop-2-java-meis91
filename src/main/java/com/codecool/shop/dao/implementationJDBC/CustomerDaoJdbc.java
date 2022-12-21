package com.codecool.shop.dao.implementationJDBC;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;

import javax.sql.DataSource;
import java.util.List;

public class CustomerDaoJdbc  implements CustomerDao {
    DataSource dataSource;

    public CustomerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
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
