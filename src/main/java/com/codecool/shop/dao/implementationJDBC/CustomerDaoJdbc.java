package com.codecool.shop.dao.implementationJDBC;

import com.codecool.shop.controller.CartController;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class CustomerDaoJdbc implements CustomerDao {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final DataSource dataSource;

    public CustomerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Customer customer) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                    INSERT INTO customer (email, password)
                    VALUES (?, ?)
                    """;
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, customer.getEMail());
            statement.setString(2, customer.getHashedPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            customer.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            logger.error("Could not save Customer into Database: {}", customer.getEMail());
            throw new RuntimeException(e);
        }

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
