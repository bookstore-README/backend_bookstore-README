package com.bookstore.readme.domain.book.exception;

import com.bookstore.readme.domain.book.controller.BookController;
import com.bookstore.readme.domain.book.response.BookResponse;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackageClasses = BookController.class)
public class BookExceptionHandler {
    /**
     * Spring Validation 오류 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BookResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String field = error.getField();
            String message = field.equals("sort") ? "유효하지 않은 Sort 값입니다." : error.getDefaultMessage();
            message = field.equals("ascending") ? "유효하지 않은 Ascending 값입니다." : message;

            errors.put(field, message);
        }

        return ResponseEntity.badRequest()
                .body(BookResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("fail")
                        .data(errors)
                        .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundBookByIdException.class)
    public ResponseEntity<BookResponse> handleNotFoundCategoryByIdException(NotFoundBookByIdException ex) {
        return ResponseEntity.badRequest()
                .body(BookResponse.builder()
                        .status(ex.getStatus())
                        .message(ex.getMessage())
                        .data(ex.getBookId())
                        .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundMemberByIdException.class)
    public ResponseEntity<BookResponse> handleNotFoundMemberByIdException(NotFoundMemberByIdException ex) {
        return ResponseEntity.badRequest()
                .body(BookResponse.builder()
                        .status(ex.getStatus())
                        .message(ex.getMessage())
                        .data(ex.getMemberId())
                        .build());
    }
}
