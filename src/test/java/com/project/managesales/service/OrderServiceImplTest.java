package com.project.managesales.service;

import com.project.managesales.entity.Customer;
import com.project.managesales.entity.Order;
import com.project.managesales.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderRepository repository;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void createOrder() {
        BigDecimal amount = new BigDecimal(1000);
        Order order = new Order(null, "A10101", amount, LocalDate.now(), new Customer());

        Order saved = new Order(1L, "A10101", amount, LocalDate.now(), new Customer());
        when(repository.save(order)).thenReturn(saved);

        Order result = orderService.createOrder(order);

        assertNotNull(result.getId());
        assertEquals(saved, result);

        verify(repository).save(order);

    }

    @Test
    void getOrders() {
    List<Order> orders = List.of(
            new Order(1L, "A10101", BigDecimal.valueOf(1000), LocalDate.now(), new Customer()),
            new Order(2L, "A10102", BigDecimal.valueOf(2000), LocalDate.now(), new Customer())
    );
    when(repository.findAll(any(Specification.class))).thenReturn(orders);

    List<Order> orderList = orderService.getOrders(null, null, null);

    assertNotNull(orderList);
    assertEquals(orders.size(), orderList.size());

    verify(repository).findAll(any(Specification.class));
    }

    @Test
    void getOrderById() {
        List<Order> orders = List.of(
                new Order(1L, "A10101", BigDecimal.valueOf(1000), LocalDate.now(), new Customer()),
                new Order(2L, "A10102", BigDecimal.valueOf(2000), LocalDate.now(), new Customer())
        );

        when(repository.findById(1L)).thenReturn(Optional.of(orders.get(0)));

        Order result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(orders.get(0), result);

        verify(repository).findById(1L);
    }
}