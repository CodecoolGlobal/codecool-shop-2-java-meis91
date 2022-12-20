package com.codecool.shop.dao.implementationJDBC;

import javax.sql.DataSource;

public abstract class DaoJdbc {
    private final DataSource dataSource;

    public DaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
