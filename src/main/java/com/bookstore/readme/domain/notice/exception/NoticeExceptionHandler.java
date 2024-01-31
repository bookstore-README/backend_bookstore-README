package com.bookstore.readme.domain.notice.exception;

import com.bookstore.readme.domain.notice.controller.NoticeController;
import com.bookstore.readme.domain.notice.response.NoticeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackageClasses = NoticeController.class)
public class NoticeExceptionHandler {

    @ExceptionHandler(value = {NotFoundNoticeByIdException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<NoticeResponse> handlerNotFoundNoticeByIdException(NotFoundNoticeByIdException ex) {
        int status = ex.getStatus();
        String message = ex.getMessage();
        NoticeResponse response = NoticeResponse.builder()
                .status(status)
                .message(message)
                .data(ex.getNoticeId())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<NoticeResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        NoticeResponse response = NoticeResponse.builder()
                .status(ex.getStatusCode().value())
                .message("fail")
                .data(errors)
                .build();
        return ResponseEntity.badRequest().body(response);
    }
}
