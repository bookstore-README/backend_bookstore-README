package com.bookstore.readme.domain.order.repository;

import com.bookstore.readme.domain.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
