package com.codecool.shop.dao.implementationJDBC;

import com.codecool.shop.controller.CartController;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Map;

public class OrderDaoJdbc implements OrderDao {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final DataSource dataSource;

    public OrderDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

//    @Override
//    public void add(Order order) {
//        try (Connection conn = dataSource.getConnection()) {
//            beginTransaction();
//            String sql = """
//                    INSERT INTO "order" (customer_id, order_date, total_price, order_status)
//                    VALUES (?, ?, ?,?)
//                    """;
//            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            statement.setInt(1, order.getCustomer().getId());
//            statement.setDate(2, order.getOrderDate());
//            statement.setBigDecimal(3, order.getTotalPrice());
//            statement.setString(4, order.getOrderStatus().toString());
//            statement.executeUpdate();
//            ResultSet resultSet = statement.getGeneratedKeys();
//            resultSet.next();
//            int orderId = resultSet.getInt(1);
//            order.setId(orderId);
//            addOrderItems(orderId, order.getOrderItems());
//            commitTransaction();
//        } catch (SQLException e) {
//            logger.error("Error while saving order from the following customer: {}", order.getCustomer().getEMail());
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void add(Order order) {
        try (Connection conn = dataSource.getConnection()) {
            String sql1 = """
                    BEGIN;
                    """;
            PreparedStatement statement1 = conn.prepareStatement(sql1);
            statement1.execute();


            String sql2 = """
                    INSERT INTO "order" (customer_id, order_date, total_price, order_status)
                    VALUES (?, ?, ?,?)
                    """;
            PreparedStatement statement2 = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
            statement2.setInt(1, order.getCustomer().getId());
            statement2.setDate(2, order.getOrderDate());
            statement2.setBigDecimal(3, order.getTotalPrice());
            statement2.setString(4, order.getOrderStatus().toString());
            statement2.executeUpdate();
            ResultSet resultSet = statement2.getGeneratedKeys();
            resultSet.next();
            int orderId = resultSet.getInt(1);
            order.setId(orderId);

            String sql3 = """
                    INSERT INTO order_item (order_id, product_id, amount)
                    VALUES (?, ?, ?)""";
            PreparedStatement statement3 = conn.prepareStatement(sql3);

            int insertCount=0;

            for (Map.Entry<Integer, Integer> item : order.getOrderItems().entrySet()) {
                statement3.setInt(1, orderId);
                statement3.setInt(2, item.getKey());
                statement3.setInt(3, item.getValue());
                statement3.addBatch();
                if (++insertCount % order.getOrderItems().size() == 0) {
                    statement3.executeBatch();
                }
            }
            statement3.executeBatch();

            String sql4 = """
                    COMMIT;
                    """;
            PreparedStatement statement = conn.prepareStatement(sql4);
            statement.execute();

        } catch (SQLException e) {
            logger.error("Error while saving order from the following customer: {}", order.getCustomer().getEMail());
            throw new RuntimeException(e);
        }
    }


    private void beginTransaction() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                    BEGIN;
                    """;

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            logger.error("Error at begin transaction");
            throw new RuntimeException(e);
        }
    }

    private void addOrderItems(int orderID, Map<Integer, Integer> orderItems) {
        final int batchSize = orderItems.size(); //Batch size is important.
        try (Connection conn = dataSource.getConnection()){
            String sql = """
                    INSERT INTO order_item (order_id, product_id, amount)
                    VALUES (?, ?, ?)""";
            PreparedStatement statement = conn.prepareStatement(sql);

            int insertCount=0;

            for (Map.Entry<Integer, Integer> item : orderItems.entrySet()) {
                statement.setInt(1, orderID);
                statement.setInt(2, item.getKey());
                statement.setInt(3, item.getValue());
                statement.addBatch();
                if (++insertCount % batchSize == 0) {
                    statement.executeBatch();
                }
            }
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private void commitTransaction() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                    COMMIT;
                    """;
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            logger.error("Error at commit transaction");
            throw new RuntimeException(e);
        }
    }
}
