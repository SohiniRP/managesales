package com.project.managesales.specification;

import com.project.managesales.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

    public static Specification<Customer> hasStatus(String status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Customer> hasNameLike(String name) {
        return (root, query, cb) ->
                (name == null || name.isBlank())
                        ? null
                        : cb.like(
                        cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );

    }
}
