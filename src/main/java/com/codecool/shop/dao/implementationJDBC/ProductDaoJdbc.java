package com.codecool.shop.dao.implementationJDBC;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.service.ProductService;
import jdk.jfr.Category;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
                    """;
            ResultSet rs = conn.createStatement().executeQuery(sql);

            List<Product> result = new ArrayList<>();
            SupplierDaoJdbc supplierDaoJdbc = new SupplierDaoJdbc(dataSource);
            ProductCategoryDaoJdbc productCategoryDaoJdbc = new ProductCategoryDaoJdbc(dataSource);
            ProductService productService = new ProductService(this);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("default_price");
                String currency = rs.getString("default_currency");
                int supplierId = rs.getInt("supplier_id");
                int categoryId = rs.getInt("category_id");

                Product product = new Product(id, name, price, currency, description, productCategoryDaoJdbc.find(categoryId), supplierDaoJdbc.find(supplierId));
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while reading all authors", e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                    SELECT *
                    FROM product 
                    WHERE supplier_id = ?
                    """;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, supplier.getId());
            ResultSet rs = st.executeQuery();
            List<Product> result = new ArrayList<>();
            SupplierDaoJdbc supplierDaoJdbc = new SupplierDaoJdbc(dataSource);
            ProductCategoryDaoJdbc productCategoryDaoJdbc = new ProductCategoryDaoJdbc(dataSource);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("default_price");
                String currency = rs.getString("default_currency");
                int supplierId = rs.getInt("supplier_id");
                int categoryId = rs.getInt("category_id");
                Product product = new Product(id, name, price, currency, description, productCategoryDaoJdbc.find(categoryId), supplierDaoJdbc.find(supplierId));
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding category", e);
        }
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                    SELECT *
                    FROM product 
                    WHERE category_id = ?
                    """;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, productCategory.getId());
            ResultSet rs = st.executeQuery();
            List<Product> result = new ArrayList<>();
            SupplierDaoJdbc supplierDaoJdbc = new SupplierDaoJdbc(dataSource);
            ProductCategoryDaoJdbc productCategoryDaoJdbc = new ProductCategoryDaoJdbc(dataSource);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("default_price");
                String currency = rs.getString("default_currency");
                int supplierId = rs.getInt("supplier_id");
                int categoryId = rs.getInt("category_id");
                Product product = new Product(id, name, price, currency, description, productCategoryDaoJdbc.find(categoryId), supplierDaoJdbc.find(supplierId));
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding category", e);
        }
    }
}
