package com.project.managesales.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.managesales.entity.Customer;
import com.project.managesales.enums.Status;
import com.project.managesales.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createCustomer() throws Exception {
        Customer input = new Customer(null, "ABCDEF", "Sohini",
                "sohini@email.com", Status.ACTIVE, LocalDateTime.now());

        Customer saved = new Customer(1L, "ABCDEF", "Sohini",
                "sohini@email.com", Status.ACTIVE, LocalDateTime.now());

        when(customerService.createCustomer(any(Customer.class)))
                .thenReturn(saved);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerCode").value("ABCDEF"))
                .andExpect(jsonPath("$.name").value("Sohini"))
                .andExpect(jsonPath("$.email").value("sohini@email.com"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(customerService).createCustomer(any(Customer.class));
    }

    @Test
    void getCustomer() throws Exception {
        List<Customer> customers = List.of(
                new Customer(1L, "ABCDEF", "Sohini", "sohini@gmail.com", Status.ACTIVE, LocalDateTime.now()),
                new Customer(2L, "BCDEFG", "SohiniP", "sohinip@gmail.com", Status.INACTIVE, LocalDateTime.now())
        );

        when(customerService.getCustomer("ACTIVE", "Sohini"))
                .thenReturn(customers);

        mockMvc.perform(get("/api/customers")
                        .param("status", "ACTIVE")
                        .param("name", "Sohini"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(customerService).getCustomer("ACTIVE", "Sohini");
    }

    @Test
    void getCustomerById() throws Exception {
        Customer customer = new Customer(
                1L, "ABCDEF", "Sohini", "sohini@gmail.com", Status.ACTIVE, LocalDateTime.now()
        );

        when(customerService.getCustomerById(1L))
                .thenReturn(customer);

        mockMvc.perform(get("/api/customers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Sohini"))
                .andExpect(jsonPath("$.email").value("sohini@gmail.com"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(customerService).getCustomerById(1L);
    }

    @Test
    void updateCustomer() throws Exception {
        Customer update = new Customer(null, "ABCDEF", "Sohini",
                "sohini@gmail.com", Status.ACTIVE, LocalDateTime.now());

        Customer updated = new Customer(1L, "ABCDEF", "Sohini",
                "sohini@gmail.com", Status.ACTIVE, LocalDateTime.now());

        when(customerService.updateCustomer(eq(1L), any(Customer.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/customers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sohini"));

        verify(customerService).updateCustomer(eq(1L), any(Customer.class));
    }

    @Test
    void deleteCustomer() throws Exception {
        doNothing().when(customerService).deleteCustomer(1L);

        mockMvc.perform(delete("/api/customers/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomer(1L);
    }
}