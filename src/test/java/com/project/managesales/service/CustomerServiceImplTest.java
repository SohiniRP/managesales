package com.project.managesales.service;

import com.project.managesales.entity.Customer;
import com.project.managesales.enums.Status;
import com.project.managesales.exception.ResourceNotFoundException;
import com.project.managesales.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerServiceImpl;

    @Test
    void createCustomer() {
        Customer input = new Customer(null, "ABCDEF", "Sohini", "sohini.paul@rightpoint.com", Status.ACTIVE, LocalDateTime.now());

        Customer saved = new Customer(1L, "ABCDEF", "Sohini", "sohini.paul@rightpoint.com", Status.ACTIVE, LocalDateTime.now());
        when(customerRepository.save(input)).thenReturn(saved);

        Customer result = customerServiceImpl.createCustomer(input);

        assertNotNull(result.getId());
        assertEquals(result, saved);

        verify(customerRepository).save(input);
    }

    @Test
    void getCustomer() {
    List<Customer> input = List.of(
            new Customer(1L, "ABCDEF", "Sohini", "sohini.paul@rightpoint.com", Status.ACTIVE, LocalDateTime.now()),
            new Customer(2L, "BCDEFG", "SohiniP", "sohinipaul@rightpoint.com", Status.INACTIVE, LocalDateTime.now())
        );
    when(customerRepository.findAll(any(Specification.class))).thenReturn(input);

    List<Customer> result = customerServiceImpl.getCustomer(null, null);

    assertNotNull(result);
    assertEquals(2, result.size());

    verify(customerRepository).findAll(any(Specification.class));
    }

    @Test
    void getCustomerById() {
        List<Customer> input = List.of(
                new Customer(1L, "ABCDEF", "Sohini", "sohini.paul@rightpoint.com", Status.ACTIVE, LocalDateTime.now()),
                new Customer(2L, "BCDEFG", "SohiniP", "sohinipaul@rightpoint.com", Status.INACTIVE, LocalDateTime.now())
        );

        when(customerRepository.findById(1l)).thenReturn(Optional.ofNullable(input.get(0)));

        Customer result = customerServiceImpl.getCustomerById(1L);

        assertNotNull(result);
        assertEquals(input.get(0), result);

        verify(customerRepository).findById(1l);
    }

    @Test
    void updateCustomerIfExists() {
        Customer existing = new Customer(
                1L, "ABCDEF", "Sohini", "sohini.paul@rightpoint.com", Status.ACTIVE, LocalDateTime.now()
        );

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        Customer update = new Customer();
        update.setName("SohiniPaul");
        update.setEmail("sohini.p@rightpoint.com");

        Customer result = customerServiceImpl.updateCustomer(1L, update);

        assertEquals("SohiniPaul", result.getName());
        assertEquals("sohini.p@rightpoint.com", result.getEmail());
        assertEquals("ABCDEF", result.getCustomerCode());
        assertEquals(Status.ACTIVE, result.getStatus());

        verify(customerRepository).findById(1L);

    }

    @Test
    void updateCustomerIfNotExists() {
        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        Customer update = new Customer();
        update.setName("SohiniPaul");

        // Act + Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> customerServiceImpl.updateCustomer(1L, update)
        );

        assertEquals("Customer not found with id 1", exception.getMessage());

        verify(customerRepository).findById(1L);
        verify(customerRepository, never()).save(any());
    }

    @Test
    void deleteCustomerIfExists() {
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        customerServiceImpl.deleteCustomer(1L);

        verify(customerRepository).delete(customer);
    }

    @Test
    void deleteCustomerIfNotExists() {
        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> customerServiceImpl.deleteCustomer(1L)
        );

        verify(customerRepository, never()).delete((Customer) any());
    }
}