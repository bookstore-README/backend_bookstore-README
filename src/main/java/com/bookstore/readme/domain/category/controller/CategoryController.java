package com.bookstore.readme.domain.category.controller;

import com.bookstore.readme.domain.category.request.CategoryDeleteRequest;
import com.bookstore.readme.domain.category.request.CategoryRequest;
import com.bookstore.readme.domain.category.request.CategoryUpdateRequest;
import com.bookstore.readme.domain.category.response.CategoryResponse;
import com.bookstore.readme.domain.category.service.CategoryService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<CategoryResponse> categoryList() {
        CacheControl cacheControl = CacheControl.maxAge(Duration.ofMinutes(1));
        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(categoryService.categoryList());
    }

    @PostMapping("/save")
    public ResponseEntity<CategoryResponse> categorySave(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.categorySave(request));
    }

    @PostMapping("/update")
    public ResponseEntity<CategoryResponse> categoryUpdate(@Valid @RequestBody CategoryUpdateRequest request) {
        return ResponseEntity.ok(categoryService.categoryUpdate(request));
    }

    @PostMapping("/delete")
    public ResponseEntity<CategoryResponse> categoryDelete(@Valid @RequestBody CategoryDeleteRequest request) {
        return ResponseEntity.ok(categoryService.categoryDelete(request.getCategoryId()));
    }
}
