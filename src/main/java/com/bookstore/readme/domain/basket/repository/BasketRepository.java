package com.bookstore.readme.domain.basket.repository;

import com.bookstore.readme.domain.basket.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    Optional<Basket> findByBookIdAndMemberId(Long bookId, Long memberId);
}
