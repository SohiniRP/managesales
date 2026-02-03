package com.project.managesales.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.managesales.entity.Customer;
import com.project.managesales.entity.Order;
import com.project.managesales.enums.Status;
import com.project.managesales.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.param;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createOrder() throws Exception {
        Order input = new Order(
                null, "ABC10101", BigDecimal.valueOf(1000), LocalDate.now(), new Customer()
        );

        Order saved = new Order(
                1L, "ABC10101", BigDecimal.valueOf(1000), LocalDate.now(), new Customer()
        );

        when(orderService.createOrder(any(Order.class))).thenReturn(saved);

        mockMvc.perform(post("/api/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(1000)));
    }

    @Test
    void getOrder() throws Exception {
        List<Order> orders = List.of(
                new Order(1L, "ABC10101", BigDecimal.valueOf(1000), LocalDate.now(), new Customer()),
                new Order(2L, "ABC10102", BigDecimal.valueOf(2000), LocalDate.now(), new Customer())
        );

        when(orderService.getOrders(BigDecimal.valueOf(1000), BigDecimal.valueOf(2000), "ABCDEF"))
                .thenReturn(orders);

        mockMvc.perform(get("/api/orders")
                        .param("minAmount", "1000")
                        .param("maxAmount", "2000")
                        .param("customerCode", "ABCDEF"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(orderService).getOrders(BigDecimal.valueOf(1000), BigDecimal.valueOf(2000), "ABCDEF");
    }

    @Test
    void getOrderById() throws Exception {
        Order order = new Order(
                1L, "ABC10101", BigDecimal.valueOf(1000), LocalDate.now(), new Customer()
        );

        when(orderService.getOrderById(1L))
                .thenReturn(order);

        mockMvc.perform(get("/api/orders/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value("1000"))
                .andExpect(jsonPath("$.orderNumber").value("ABC10101"));

        verify(orderService).getOrderById(1L);
    }
}