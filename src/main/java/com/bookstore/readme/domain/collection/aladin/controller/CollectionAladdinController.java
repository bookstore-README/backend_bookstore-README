package com.bookstore.readme.domain.collection.aladin.controller;

import com.bookstore.readme.domain.collection.aladin.dto.BookDto;
import com.bookstore.readme.domain.collection.aladin.request.list.AladdinListRequest;
import com.bookstore.readme.domain.collection.aladin.service.*;
import com.bookstore.readme.domain.collection.aladin.request.product.AladdinProductRequest;
import com.bookstore.readme.domain.collection.aladin.request.search.AladdinSearchRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 시큐리티로 외부 노출 막기
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/collection/aladdin")
public class CollectionAladdinController {
    private final CollectionService collectionService;

    @GetMapping("/search")
    public ResponseEntity<BookDto> search(@Valid AladdinSearchRequest request) {
        return ResponseEntity.ok(collectionService.search(request));
    }

    @GetMapping("/list")
    public ResponseEntity<BookDto> list(@Valid AladdinListRequest request) {
        return ResponseEntity.ok(collectionService.list(request));
    }

    @GetMapping("/product")
    public ResponseEntity<BookDto> product(@Valid AladdinProductRequest request) {
        return ResponseEntity.ok(collectionService.product(request));
    }
}

