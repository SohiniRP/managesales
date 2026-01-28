package com.project.managesales.service;

import com.project.managesales.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);
    List<Customer> getCustomer(String status, String name);
    Customer getCustomerById(Long id);
    Customer updateCustomer(Long id, Customer customer);
    boolean deleteCustomer(Long id);
}
