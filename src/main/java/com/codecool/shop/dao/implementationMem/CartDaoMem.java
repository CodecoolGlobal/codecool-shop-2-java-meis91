package com.codecool.shop.dao.implementationMem;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartDaoMem implements CartDao {

    private List<Product> data = new ArrayList<>();
    private Customer customer;

    private static CartDaoMem instance = null;

    private CartDaoMem() {

    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        data.add(product);
    }

    @Override
    public Product find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public void removeAll() {
        data = new ArrayList<>();
    }

    @Override
    public List<Product> getAll() {
        return data;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return data.stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());

    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return data.stream().filter(t -> t.getProductCategory().equals(productCategory)).collect(Collectors.toList());

    }

    @Override
    public String getTotalPrice() {
        BigDecimal totalPrice = data.stream().map(Product -> Product.getDefaultPrice().multiply(BigDecimal.valueOf(Product.getCountOfProduct())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return String.valueOf(totalPrice) + " " + "USD";
    }
    @Override
    public String getTax() {
        BigDecimal tax = data.stream().map(Product -> Product.getDefaultPrice().multiply(BigDecimal.valueOf(Product.getCountOfProduct())))
                .reduce(BigDecimal.ZERO, BigDecimal::add).multiply(BigDecimal.valueOf(0.2));
        return String.valueOf(tax) + " " + "USD";
    }
    @Override
    public String getTaxedTotalPrice() {
        BigDecimal totalPrice = data.stream().map(Product -> Product.getDefaultPrice().multiply(BigDecimal.valueOf(Product.getCountOfProduct())))
                .reduce(BigDecimal.ZERO, BigDecimal::add).multiply(BigDecimal.valueOf(1.2));
        return String.valueOf(totalPrice) + " " + "USD";
    }
    @Override
    public Customer getCustomer() {
        return customer;
    }
    @Override
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
