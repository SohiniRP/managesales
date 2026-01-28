package com.project.managesales.service;

import com.project.managesales.entity.Customer;
import com.project.managesales.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    Order createOrder(Order order);
    List<Order> getOrders(BigDecimal amount, String customerCode);
    Order getOrderById(Long id);
}
