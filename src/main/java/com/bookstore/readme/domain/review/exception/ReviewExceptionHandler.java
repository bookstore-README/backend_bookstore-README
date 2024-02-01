package com.bookstore.readme.domain.review.exception;

import com.bookstore.readme.domain.review.controller.ReviewController;
import com.bookstore.readme.domain.review.response.ReviewResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = ReviewController.class)
public class ReviewExceptionHandler {

    @ExceptionHandler(NotFoundReviewByIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ReviewResponse> handlerNotFoundReviewByIdException(NotFoundReviewByIdException ex) {
        ReviewResponse response = ReviewResponse.builder()
                .status(ex.getStatus())
                .message(ex.getMessage())
                .data(ex.getReviewId())
                .build();

        return ResponseEntity.badRequest().body(response);
    }
}
