package com.project.managesales.controller;

import com.project.managesales.entity.Order;
import com.project.managesales.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrder(
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam(required = false) String customerCode
            ) {
        return new ResponseEntity<>(orderService.getOrders(amount, customerCode), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@RequestParam Long id) {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }
}
