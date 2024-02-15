package com.bookstore.readme.domain.order.repository;

import com.bookstore.readme.domain.order.domain.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
}
