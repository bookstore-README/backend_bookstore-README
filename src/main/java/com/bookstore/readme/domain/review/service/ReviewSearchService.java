package com.bookstore.readme.domain.review.service;

import com.bookstore.readme.domain.review.domain.Review;
import com.bookstore.readme.domain.review.dto.ReviewDto;
import com.bookstore.readme.domain.review.dto.ReviewSearchDto;
import com.bookstore.readme.domain.review.exception.NotFoundReviewByIdException;
import com.bookstore.readme.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewSearchService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewSearchDto searchReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundReviewByIdException(reviewId));

        return ReviewSearchDto.of(review);
    }
}
