package com.bookstore.readme.domain.delivery.exception;

import com.bookstore.readme.domain.delivery.controller.DeliveryController;
import com.bookstore.readme.domain.delivery.response.DeliveryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = DeliveryController.class)
public class DeliveryExceptionHandler {

    @ExceptionHandler(DeliverySaveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<DeliveryResponse> handler(DeliverySaveException e) {

        DeliveryResponse response = DeliveryResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundDeliveryByIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<DeliveryResponse> handler(NotFoundDeliveryByIdException e) {

        DeliveryResponse response = DeliveryResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .data(e.getDeliveryId())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundDeliveryByMemberIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<DeliveryResponse> handler(NotFoundDeliveryByMemberIdException e) {

        DeliveryResponse response = DeliveryResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .data(e.getMemberId())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotEqualDeliveryStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<DeliveryResponse> handler(NotEqualDeliveryStatusException e) {

        DeliveryResponse response = DeliveryResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .data(e.getDeliveryStatus())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

}
