package com.codecool.shop.dao.implementationJDBC;

import com.codecool.shop.controller.CartController;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
                    INSERT INTO customer (email, password, salt)
                    VALUES (?, ?, ?)
                    """;
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, customer.getEMail());
            statement.setString(2, customer.getHashedPassword());
            statement.setBytes(3, customer.getSalt());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            customer.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            logger.error("Could not save Customer into Database - email already exists: {}", customer.getEMail());
            throw new RuntimeException(e);
        }

    }

    @Override
    public Customer find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                    SELECT email, password, cart_id, billing_address_id, shipping_address_id
                    FROM customer
                    WHERE id = ?
                    """;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }

            String email = rs.getString(2);
            String password = rs.getString(3);
            int cartId = rs.getInt(4);
            int billingAddressId = rs.getInt(5);
            int shippingAddressId = rs.getInt(5);
            Customer customer = new Customer(email, password, null, null, null);
            return customer;
        } catch (SQLException e) {
            logger.error("Could not load customer data");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Customer> getAll() {
        return null;
    }

    public Customer getRegisteredUser(String email) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                    SELECT id, password, salt
                    FROM customer
                    WHERE email = ?
                    """;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            int id = rs.getInt(1);
            String password = rs.getString(2);
            byte[] salt = rs.getBytes(3);

            Customer customer = new Customer(email, password, salt);
            customer.setId(id);
            return customer;
        } catch (SQLException e) {
            logger.error("Could not load customer data");
            throw new RuntimeException(e);
        }
    }
}
