package com.bookstore.readme.domain.review.service;

import com.bookstore.readme.domain.review.domain.Review;
import com.bookstore.readme.domain.review.dto.ReviewDto;
import com.bookstore.readme.domain.review.exception.NotFoundReviewByIdException;
import com.bookstore.readme.domain.review.repository.ReviewRepository;
import com.bookstore.readme.domain.review.request.ReviewUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewUpdateService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewDto update(Integer reviewId, ReviewUpdateRequest request) {
        Review review = reviewRepository.findById(reviewId.longValue())
                .orElseThrow(() -> new NotFoundReviewByIdException(reviewId.longValue()));

        review.changeContent(request.getContent());
        review.changeReviewRating(request.getRating());
        reviewRepository.flush();

        return ReviewDto.of(review);
    }
}
