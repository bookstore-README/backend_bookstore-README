package com.bookstore.readme.domain.delivery.repository;

import com.bookstore.readme.domain.delivery.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByMemberId(Long memberId);

    Optional<Delivery> findByIdAndMemberId(Long id, Long memberId);

}
