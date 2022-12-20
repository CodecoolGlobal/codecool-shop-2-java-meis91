package com.codecool.shop.dao.implementationJDBC;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class ProductCategoryDaoJdbc implements ProductCategoryDao {
   /* public ProductCategoryDaoJdbc(DataSource dataSource) throws SQLException {
        super(dataSource);
    }*/

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
