package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductCategory extends BaseModel {
    private String department;
    private transient List<Product> products;

    public ProductCategory(int id, String name, String department, String description) {
        super(id, name, description);
        this.department = department;
        this.products = new ArrayList<>();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    @Override
    public String toString() {
        return String.format(
                "id: %1$d," +
                        "name: %2$s, " +
                        "department: %3$s, " +
                        "description: %4$s",
                this.id,
                this.name,
                this.department,
                this.description);
    }
}