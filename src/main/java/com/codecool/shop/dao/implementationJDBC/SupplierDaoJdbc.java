package com.codecool.shop.dao.implementationJDBC;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.util.List;

public class SupplierDaoJdbc extends DaoJdbc implements SupplierDao {
    public SupplierDaoJdbc(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void add(Supplier supplier) {

    }

    @Override
    public Supplier find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
        return null;
    }
}
