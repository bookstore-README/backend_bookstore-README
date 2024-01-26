package com.bookstore.readme.domain.review.controller;

import com.bookstore.readme.domain.review.request.ReviewRequest;
import com.bookstore.readme.domain.review.response.ReviewResponse;
import com.bookstore.readme.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/list")
    public ResponseEntity<ReviewResponse> reviewList() {
        ReviewResponse reviewResponse = reviewService.reviewList();
        return ResponseEntity.ok(reviewResponse);
    }

    @GetMapping("/search/{reviewId}")
    public ResponseEntity<ReviewResponse> searchReview(@PathVariable("reviewId") Integer review) {
        ReviewResponse reviewResponse = reviewService.searchReview(review.longValue());
        return ResponseEntity.ok(reviewResponse);
    }

    @PostMapping("/save")
    public ResponseEntity<ReviewResponse> saveReview(@Valid @RequestBody ReviewRequest request) {
        ReviewResponse reviewResponse = reviewService.saveReview(request);
        return ResponseEntity.ok(reviewResponse);
    }
}
