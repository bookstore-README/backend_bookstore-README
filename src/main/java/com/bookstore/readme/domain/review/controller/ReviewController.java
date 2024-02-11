package com.bookstore.readme.domain.review.controller;

import com.bookstore.readme.domain.review.dto.ReviewDto;
import com.bookstore.readme.domain.review.dto.ReviewSearchDto;
import com.bookstore.readme.domain.review.request.ReviewRequest;
import com.bookstore.readme.domain.review.request.ReviewSaveRequest;
import com.bookstore.readme.domain.review.request.ReviewUpdateRequest;
import com.bookstore.readme.domain.review.response.ReviewResponse;
import com.bookstore.readme.domain.review.service.*;
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
    private final ReviewSearchService reviewSearchService;
    private final ReviewSaveService reviewSaveService;
    private final ReviewDeleteService reviewDeleteService;
    private final ReviewUpdateService reviewUpdateService;

    @GetMapping("/{reviewId}")
    @Operation(summary = "리뷰 단일 조회", description = "리뷰 아이디로 단일 조회하는 API")
    public ResponseEntity<ReviewResponse> searchReview(
            @Parameter(description = "조회할 리뷰 아이디", example = "1", required = true)
            @PathVariable("reviewId") Integer reviewId
    ) {
        ReviewSearchDto reviewSearchDto = reviewSearchService.searchReview(reviewId.longValue());
        return ResponseEntity.ok(ReviewResponse.ok(reviewSearchDto));
    }

    @PostMapping
    @Operation(summary = "리뷰 등록", description = "리뷰를 등록하는 API")
    public ResponseEntity<ReviewResponse> saveReview(@Valid @RequestBody ReviewSaveRequest request) {
        Long reviewId = reviewSaveService.save(request);
        return ResponseEntity.ok(ReviewResponse.ok(reviewId));
    }

    @PutMapping
    @Operation(summary = "리뷰 수정", description = "리뷰를 수정하는 API")
    public ResponseEntity<ReviewResponse> updateReview(@Valid @RequestBody ReviewUpdateRequest request) {
        ReviewDto update = reviewUpdateService.update(request);
        return ResponseEntity.ok(ReviewResponse.ok(update));
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "리뷰 아이디로 삭제하는 API")
    public ResponseEntity<ReviewResponse> deleteReview(
            @Parameter(description = "삭제할 리뷰 아이디", example = "1", required = true)
            @PathVariable("reviewId") Integer reviewId
    ) {
        Long deleteReviewId = reviewDeleteService.delete(reviewId.longValue());
        return ResponseEntity.ok(ReviewResponse.ok(deleteReviewId));
    }
}
