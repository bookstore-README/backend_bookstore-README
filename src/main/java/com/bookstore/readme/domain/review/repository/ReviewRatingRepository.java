package com.bookstore.readme.domain.review.repository;

import com.bookstore.readme.domain.review.domain.ReviewRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRatingRepository extends JpaRepository<ReviewRating, Long> {
}
