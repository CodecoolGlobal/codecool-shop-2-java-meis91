package com.codecool.shop.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Date;
import java.util.Map;

public class Order {
    private int id;
    private final Customer customer;
    private final Date orderDate;
    private final BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private final Map<Integer, Integer> orderItems;

    public Order(Customer customer, Date orderDate, BigDecimal totalPrice, OrderStatus orderStatus, Map<Integer, Integer> orderItems) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Map<Integer, Integer> getOrderItems() {
        return orderItems;
    }
}
