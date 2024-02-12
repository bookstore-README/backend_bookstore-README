package com.bookstore.readme.domain.basket.exception;

import com.bookstore.readme.domain.basket.controller.BasketController;
import com.bookstore.readme.domain.basket.response.BasketResponse;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = BasketController.class)
public class BasketExceptionHandler {
    @ExceptionHandler(NotFoundMemberByIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BasketResponse> handlerNotFoundMemberByIdException(NotFoundMemberByIdException ex) {
        return ResponseEntity.badRequest()
                .body(BasketResponse
                        .builder()
                        .status(ex.getStatus())
                        .message(ex.getMessage())
                        .data(ex.getMemberId())
                        .build());
    }

    @ExceptionHandler(NotFoundBookByIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BasketResponse> handlerNotFoundBookByIdException(NotFoundBookByIdException ex) {
        return ResponseEntity.badRequest()
                .body(BasketResponse
                        .builder()
                        .status(ex.getStatus())
                        .message(ex.getMessage())
                        .data(ex.getBookId())
                        .build());
    }
}
