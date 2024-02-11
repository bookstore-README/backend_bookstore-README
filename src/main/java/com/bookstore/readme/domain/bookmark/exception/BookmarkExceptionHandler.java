package com.bookstore.readme.domain.bookmark.exception;

import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.bookmark.controller.BookmarkController;
import com.bookstore.readme.domain.bookmark.response.BookmarkResponse;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice(basePackageClasses = BookmarkController.class)
public class BookmarkExceptionHandler {

    @ExceptionHandler(NotFoundBookByIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BookmarkResponse> handlerNotFoundBookByIdException(NotFoundBookByIdException ex) {
        BookmarkResponse response = BookmarkResponse.builder()
                .status(ex.getStatus())
                .message(ex.getMessage())
                .data(ex.getBookId())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundMemberByIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BookmarkResponse> handlerNotFoundMemberByIdException(NotFoundMemberByIdException ex) {
        BookmarkResponse response = BookmarkResponse.builder()
                .status(ex.getStatus())
                .message(ex.getMessage())
                .data(ex.getMemberId())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BookmarkResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        BookmarkResponse response = BookmarkResponse.builder()
                .status(ex.getStatusCode().value())
                .message("fail")
                .data(errors)
                .build();

        return ResponseEntity.badRequest().body(response);
    }
}
