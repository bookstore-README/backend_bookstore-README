package com.bookstore.readme.domain.category.exception;

import com.bookstore.readme.domain.category.controller.CategoryController;
import com.bookstore.readme.domain.category.response.CategoryResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackageClasses = CategoryController.class)
public class CategoryExceptionHandler {

    /**
     * Spring Validation 오류 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CategoryResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest()
                .body(CategoryResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("fail")
                        .data(errors)
                        .build());
    }


    /**
     * ex) Integer 로 받아야할 데이터가 String 으로 들어오면 발생하는 에러 처리
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CategoryResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = "요청의 형식이 올바르지 않습니다.";

        if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {
            String fieldName = invalidFormatException.getPath().get(0).getFieldName();
            Class<?> targetType = invalidFormatException.getTargetType();
            String actualValue = invalidFormatException.getValue().toString();

            errorMessage = String.format("'%s' 필드의 값 '%s'은(는) '%s' 형식으로 제공되어야 합니다.", fieldName, actualValue, targetType.getSimpleName());
        }

        return ResponseEntity.badRequest()
                .body(CategoryResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(errorMessage)
                        .data(false)
                        .build());
    }

    @ExceptionHandler(NotFoundCategoryByIdException.class)
    public ResponseEntity<CategoryResponse> handleNotFoundCategoryByIdException(NotFoundCategoryByIdException ex) {
        return ResponseEntity.badRequest()
                .body(CategoryResponse.builder()
                        .status(ex.getStatus())
                        .message(ex.getMessage())
                        .data(ex.getCategoryId())
                        .build());
    }

    @ExceptionHandler(DuplicationCategoryNameException.class)
    public ResponseEntity<CategoryResponse> handleDuplicationCategoryNameException(DuplicationCategoryNameException ex) {
        return ResponseEntity.badRequest()
                .body(CategoryResponse.builder()
                        .status(ex.getStatus())
                        .message(ex.getMessage())
                        .data(ex.getCategoryName())
                        .build());
    }
}
