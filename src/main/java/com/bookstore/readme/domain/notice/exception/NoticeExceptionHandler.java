package com.bookstore.readme.domain.notice.exception;

import com.bookstore.readme.domain.notice.controller.NoticeController;
import com.bookstore.readme.domain.notice.response.NoticeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
