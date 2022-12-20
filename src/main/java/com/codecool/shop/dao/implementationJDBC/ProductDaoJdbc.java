package com.codecool.shop.dao.implementationJDBC;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import jdk.jfr.Category;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.spi.CurrencyNameProvider;

public class ProductDaoJdbc implements ProductDao {

    private final DataSource dataSource;

    public ProductDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Product product) {

    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                    SELECT *
                    FROM product
                    JOIN category c on c.id = product.category_id
                    JOIN supplier s on s.id = product.supplier_id
                    """;
            ResultSet rs = conn.createStatement().executeQuery(sql);

            List<Product> result = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("default_price");
                String currency = rs.getString("default_currency");
                String cName = rs.getString(9);
                //System.out.println("cName = " + cName);
                //ProductCategory productCategory = new ProductCategory(cName, rs.getString(11), rs.getString(10));
                Supplier supplier = new Supplier(rs.getString(13), rs.getString(14));
                //Product product = new Product(name, price, currency, description, productCategory, supplier);
                //result.add(product);
                //System.out.println(name + " " + description);
                /*Product product = new Product(name, description, )
                HashMap<String, String> gameState = new HashMap<>();
                gameState.put("id", rs.getString(1));
                gameState.put("date", rs.getString(2));
                gameState.put("playerId", rs.getString(3));
                gameState.put("playerName", rs.getString(4));
                result.add(gameState);*/
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while reading all authors", e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }
}
