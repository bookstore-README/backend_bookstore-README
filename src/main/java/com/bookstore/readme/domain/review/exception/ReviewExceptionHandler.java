package com.bookstore.readme.domain.review.exception;

import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.response.BookResponse;
import com.bookstore.readme.domain.review.controller.ReviewController;
import com.bookstore.readme.domain.review.response.ReviewResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(NotFoundBookByIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ReviewResponse> handlerNotFoundBookByIdException(NotFoundBookByIdException ex) {
        ReviewResponse response = ReviewResponse.builder()
                .status(ex.getStatus())
                .message(ex.getMessage())
                .data(ex.getBookId())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ReviewResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest()
                .body(ReviewResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("fail")
                        .data(errors)
                        .build());
    }

}
