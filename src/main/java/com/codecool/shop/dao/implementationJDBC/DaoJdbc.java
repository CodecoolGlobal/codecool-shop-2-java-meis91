package com.codecool.shop.dao.implementationJDBC;

import javax.sql.DataSource;
import java.sql.SQLException;

public abstract class DaoJdbc {

    DatabaseManager databaseManager = new DatabaseManager();
    protected DataSource dataSource = databaseManager.connect();

    public DaoJdbc(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
    }
}
