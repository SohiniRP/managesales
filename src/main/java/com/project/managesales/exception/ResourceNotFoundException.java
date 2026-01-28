package com.project.managesales.exception;

import com.project.managesales.repository.CustomerRepository;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
