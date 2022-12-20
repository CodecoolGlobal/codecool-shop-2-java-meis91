package com.codecool.shop.dao.implementationJDBC;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import javax.sql.DataSource;
import java.util.List;

public class ProductCategoryDaoJdbc extends DaoJdbc implements ProductCategoryDao {
    public ProductCategoryDaoJdbc(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void add(ProductCategory category) {

    }

    @Override
    public ProductCategory find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        return null;
    }
}
