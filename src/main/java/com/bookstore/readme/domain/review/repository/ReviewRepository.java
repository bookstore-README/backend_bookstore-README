package com.bookstore.readme.domain.review.repository;

import com.bookstore.readme.domain.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
