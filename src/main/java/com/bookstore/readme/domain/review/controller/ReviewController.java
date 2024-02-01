package com.bookstore.readme.domain.review.controller;

import com.bookstore.readme.domain.review.dto.ReviewSearchDto;
import com.bookstore.readme.domain.review.request.ReviewRequest;
import com.bookstore.readme.domain.review.response.ReviewResponse;
import com.bookstore.readme.domain.review.service.ReviewSearchService;
import com.bookstore.readme.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Tag(name = "리뷰 API")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewSearchService reviewSearchService;

    @GetMapping("/list")
    public ResponseEntity<ReviewResponse> reviewList() {
        ReviewResponse reviewResponse = reviewService.reviewList();
        return ResponseEntity.ok(reviewResponse);
    }

    @GetMapping("/search/{reviewId}")
    @Operation(summary = "리뷰 단일 조회", description = "리뷰 아이디로 단일 조회하는 API")
    public ResponseEntity<ReviewResponse> searchReview(
            @Parameter(description = "조회할 리뷰 아이디", example = "1", required = true)
            @PathVariable("reviewId") Integer review) {
        ReviewSearchDto reviewSearchDto = reviewSearchService.searchReview(review.longValue());
        return ResponseEntity.ok(ReviewResponse.ok(reviewSearchDto));
    }

    @PostMapping("/save")
    public ResponseEntity<ReviewResponse> saveReview(@Valid @RequestBody ReviewRequest request) {
        ReviewResponse reviewResponse = reviewService.saveReview(request);
        return ResponseEntity.ok(reviewResponse);
    }
}
