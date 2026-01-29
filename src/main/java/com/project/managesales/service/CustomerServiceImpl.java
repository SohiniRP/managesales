package com.project.managesales.service;

import com.project.managesales.entity.Customer;
import com.project.managesales.exception.ResourceNotFoundException;
import com.project.managesales.exception.BadRequestException;
import com.project.managesales.repository.CustomerRepository;
import com.project.managesales.specification.CustomerSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public List<Customer> getCustomer(String status, String name) {

        Specification<Customer> spec = Specification
                .where(CustomerSpecification.hasStatus(status))
                .and(CustomerSpecification.hasNameLike(name));

        return repository.findAll(spec);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Customer not found with id " + id));
    }

    @Override
    @Transactional
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = getCustomerById(id);

        if (customer.getStatus() == null && existing.getStatus() == null) {
            throw new BadRequestException("Status cannot be null");
        }

        if(customer.getName()!=null) existing.setName(customer.getName());
        if(customer.getEmail()!=null) existing.setEmail(customer.getEmail());
        if(customer.getCustomerCode()!=null) existing.setCustomerCode(customer.getCustomerCode());
        if(customer.getStatus()!=null) existing.setStatus(customer.getStatus());

        return existing;
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        repository.delete(customer);
    }

}
