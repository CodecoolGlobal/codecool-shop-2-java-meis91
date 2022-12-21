package com.codecool.shop.dao.implementationJDBC;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJdbc  implements SupplierDao {

    private final DataSource dataSource;

    public SupplierDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Supplier supplier) {

    }

    @Override
    public Supplier find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                    SELECT *
                    FROM supplier 
                    WHERE id = ?
                    """;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            String name = rs.getString("name");
            String description = rs.getString("description");
            return new Supplier(id, name, description);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while finding supplier", e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                    SELECT *
                    FROM supplier
                    """;
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Supplier> result = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Supplier supplier = new Supplier(id, name, description);
                result.add(supplier);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while reading all suppliers", e);
        }

    }
}
