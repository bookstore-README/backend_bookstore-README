package com.bookstore.readme.domain.delivery.repository;

import com.bookstore.readme.domain.delivery.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
