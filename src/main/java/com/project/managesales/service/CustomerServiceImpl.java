package com.project.managesales.service;

import com.project.managesales.entity.Customer;
import com.project.managesales.exception.ResourceNotFoundException;
import com.project.managesales.repository.CustomerRepository;
import com.project.managesales.specification.CustomerSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = getCustomerById(id);
        if(existing == null){
            throw new ResourceNotFoundException("Customer not found with id " + id);
        } else{
            existing.setName(customer.getName()!=null?customer.getName():existing.getName());
            existing.setEmail(customer.getEmail()!=null?customer.getEmail():existing.getEmail());
            existing.setCustomerCode(customer.getCustomerCode()!=null?customer.getCustomerCode():existing.getCustomerCode());
            existing.setStatus(customer.getStatus()!=null?customer.getStatus():existing.getStatus());
        }
        return existing;
    }

    @Override
    public boolean deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        if(customer != null){
            repository.delete(customer);
            return true;
        }
        return false;
    }
}
