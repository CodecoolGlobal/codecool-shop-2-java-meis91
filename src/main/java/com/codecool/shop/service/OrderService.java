package com.codecool.shop.service;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

public class OrderService {

    private final OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void add(Order order) {
        orderDao.add(order);
    }
}
