package com.bookstore.readme.domain.review.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.service.BookQueryService;
import com.bookstore.readme.domain.review.domain.Review;
import com.bookstore.readme.domain.review.dto.ReviewDto;
import com.bookstore.readme.domain.review.dto.ReviewListDto;
import com.bookstore.readme.domain.review.request.ReviewRequest;
import com.bookstore.readme.domain.review.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Primary
@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewDefaultService implements ReviewService {

    private final ReviewQueryService reviewQueryService;

    @Override
    public ReviewResponse reviewList() {
        List<Review> reviews = reviewQueryService.findAll();
        List<ReviewDto> convertReviews = reviews.stream()
                .map(ReviewDto::of)
                .toList();

        ReviewListDto result = ReviewListDto.builder()
                .total(convertReviews.size())
                .limit(convertReviews.size())
                .page(1)
                .reviews(convertReviews)
                .build();

        return ReviewResponse.ok(result);
    }
}
