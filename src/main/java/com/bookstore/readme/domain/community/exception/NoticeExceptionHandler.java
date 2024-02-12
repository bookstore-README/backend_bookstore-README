package com.bookstore.readme.domain.community.exception;

import com.bookstore.readme.domain.community.controller.CommunityController;
import com.bookstore.readme.domain.community.response.CommunityResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackageClasses = CommunityController.class)
public class NoticeExceptionHandler {

    @ExceptionHandler(value = {NotFoundNoticeByIdException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommunityResponse> handlerNotFoundNoticeByIdException(NotFoundNoticeByIdException ex) {
        int status = ex.getStatus();
        String message = ex.getMessage();
        CommunityResponse response = CommunityResponse.builder()
                .status(status)
                .message(message)
                .data(ex.getNoticeId())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommunityResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        CommunityResponse response = CommunityResponse.builder()
                .status(ex.getStatusCode().value())
                .message("fail")
                .data(errors)
                .build();
        return ResponseEntity.badRequest().body(response);
    }
}
