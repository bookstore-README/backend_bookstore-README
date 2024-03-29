package com.bookstore.readme.domain.review.controller;

import com.bookstore.readme.domain.member.model.MemberDetails;
import com.bookstore.readme.domain.review.dto.ReviewDto;
import com.bookstore.readme.domain.review.dto.ReviewListDto;
import com.bookstore.readme.domain.review.dto.ReviewPageDto;
import com.bookstore.readme.domain.review.dto.ReviewSearchDto;
import com.bookstore.readme.domain.review.request.ReviewPageRequest;
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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Tag(name = "리뷰 API")
public class ReviewController {
    private final ReviewSearchService reviewSearchService;
    private final ReviewSaveService reviewSaveService;
    private final ReviewDeleteService reviewDeleteService;
    private final ReviewUpdateService reviewUpdateService;
    private final ReviewPageService reviewPageService;

    @GetMapping("/{reviewId}")
    @Operation(summary = "리뷰 단일 조회", description = "리뷰 아이디로 단일 조회하는 API")
    public ResponseEntity<ReviewResponse> searchReview(
            @Parameter(description = "조회할 리뷰 아이디", example = "1", required = true)
            @PathVariable("reviewId") Integer reviewId
    ) {
        ReviewSearchDto reviewSearchDto = reviewSearchService.searchReview(reviewId.longValue());
        return ResponseEntity.ok(ReviewResponse.ok(reviewSearchDto));
    }

    @GetMapping("/{bookId}/book")
    @Operation(summary = "도서 아이디로 리뷰 조회", description = "도서 아이디로 리뷰 전체 페이징 조회 API")
    public ResponseEntity<ReviewResponse> searchReviewByBookId(
            @PathVariable(name = "bookId") Integer bookId,
            @ParameterObject @Valid ReviewPageRequest request
    ) {
        ReviewPageDto reviewPageDto = reviewPageService.searchPage(bookId.longValue(), request);
        return ResponseEntity.ok(ReviewResponse.ok(reviewPageDto));
    }

    @GetMapping
    @Operation(summary = "로그인 한 회원의 리뷰 조회", description = "로그인 한 회원의 리뷰 목록 조회하는 API")
    public ResponseEntity<ReviewResponse> searchReviewByMemberId(
            @AuthenticationPrincipal MemberDetails memberDetails
//            @Parameter(description = "조회할 회원 아이디", example = "1", required = true)
//            @PathVariable("memberId") Integer reviewId
    ) {
        ReviewListDto result = reviewSearchService.searchReviewByMemberId(memberDetails.getMemberId());
        return ResponseEntity.ok(ReviewResponse.ok(result));
    }

    @PostMapping
    @Operation(summary = "리뷰 등록", description = "리뷰를 등록하는 API")
    public ResponseEntity<ReviewResponse> saveReview(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Valid @RequestBody ReviewSaveRequest request) {
        Long reviewId = reviewSaveService.save(memberDetails, request);
        return ResponseEntity.ok(ReviewResponse.ok(reviewId));
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "리뷰를 수정하는 API")
    public ResponseEntity<ReviewResponse> updateReview(
            @Parameter(description = "수정할 리뷰 아이디", required = true) @PathVariable(name = "reviewId") Integer reviewId,
            @Valid @RequestBody ReviewUpdateRequest request) {
        ReviewDto update = reviewUpdateService.update(reviewId, request);
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
