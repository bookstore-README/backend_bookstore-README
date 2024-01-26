package com.bookstore.readme.domain.review.service;

import com.bookstore.readme.domain.review.domain.Review;
import com.bookstore.readme.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewQueryService {
    private final ReviewRepository reviewRepository;

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseGet(() -> null);
    }

    public Long save(Review review) {
        Review result = reviewRepository.save(review);
        return result.getId();
    }
}
