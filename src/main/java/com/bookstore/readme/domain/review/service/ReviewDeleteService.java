package com.bookstore.readme.domain.review.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.review.domain.Review;
import com.bookstore.readme.domain.review.exception.NotFoundReviewByIdException;
import com.bookstore.readme.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewDeleteService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public Long delete(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).
                orElseThrow(() -> new NotFoundReviewByIdException(reviewId));

        Book book = review.getBook();
        book.getReviews().remove(review);
        book.subReviewCount();
        reviewRepository.delete(review);
        return reviewId;
    }
}
