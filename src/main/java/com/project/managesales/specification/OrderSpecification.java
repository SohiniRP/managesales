package com.project.managesales.specification;

import com.project.managesales.entity.Customer;
import com.project.managesales.entity.Order;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class OrderSpecification {

    public static Specification<Order> amountGreaterThanOrEqual(BigDecimal min) {
        return (root, query, cb) ->
                min == null ? null : cb.greaterThanOrEqualTo(root.get("amount"), min);
    }

    public static Specification<Order> amountLessThanOrEqual(BigDecimal max) {
        return (root, query, cb) ->
                max == null ? null : cb.lessThanOrEqualTo(root.get("amount"), max);
    }

    public static Specification<Order> hasCustomerCode(String customerCode) {
        return (root, query, cb) -> {
            if (customerCode == null || customerCode.isBlank()) {
                return null;
            }
            Join<Order, Customer> customerJoin = root.join("customer");
            return cb.equal(customerJoin.get("code"), customerCode);
        };
    }
}

