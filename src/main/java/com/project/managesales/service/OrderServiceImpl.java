package com.project.managesales.service;

import com.project.managesales.entity.Customer;
import com.project.managesales.entity.Order;
import com.project.managesales.exception.ResourceNotFoundException;
import com.project.managesales.repository.OrderRepository;
import com.project.managesales.specification.OrderSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order createOrder(Order order) {
        return repository.save(order);
    }

    @Override
    public List<Order> getOrders(BigDecimal minAmount, BigDecimal maxAmount, String customerCode) {

        Specification<Order> specification = Specification
                .where(OrderSpecification.amountGreaterThanOrEqual(minAmount))
                .and(OrderSpecification.amountLessThanOrEqual(maxAmount))
                .and(OrderSpecification.hasCustomerCode(customerCode));

        return repository.findAll(specification);
    }

    @Override
    public Order getOrderById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order Not Found! - "+ id));
    }
}
