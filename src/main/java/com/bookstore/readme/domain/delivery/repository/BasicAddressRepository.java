package com.bookstore.readme.domain.delivery.repository;

import com.bookstore.readme.domain.delivery.domain.BasicAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicAddressRepository extends JpaRepository<BasicAddress, Long> {
}
