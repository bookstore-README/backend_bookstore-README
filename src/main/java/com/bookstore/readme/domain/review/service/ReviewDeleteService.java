package com.bookstore.readme.domain.review.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.review.domain.Review;
import com.bookstore.readme.domain.review.exception.NotFoundReviewByIdException;
import com.bookstore.readme.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        //도서 별점 수정
        List<Review> reviews = book.getReviews();
        Double newRating = 0D;
        for (Review ratingReview : reviews) {
            Double reviewRating = ratingReview.getReviewRating();
            newRating += reviewRating;
        }

        newRating = reviews.isEmpty() ? 0D : newRating / reviews.size();
        book.changeRating(newRating);


        reviewRepository.delete(review);
        return reviewId;
    }
}
