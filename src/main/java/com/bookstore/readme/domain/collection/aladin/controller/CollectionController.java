package com.bookstore.readme.domain.collection.controller;

import com.bookstore.readme.domain.collection.request.list.AladdinListRequest;
import com.bookstore.readme.domain.collection.request.product.AladdinProductRequest;
import com.bookstore.readme.domain.collection.request.search.AladdinSearchRequest;
import com.bookstore.readme.domain.collection.service.CollectionService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
@RequestMapping("/collection")
public class CollectionController {
    private final CollectionService service;

    @GetMapping("/aladdin/search")
    public ResponseEntity<Object> search(@Valid AladdinSearchRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(service.search(request));
    }

    @GetMapping("/aladdin/list")
    public ResponseEntity<Object> list(@Valid AladdinListRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(service.list(request));
    }

    @GetMapping("/aladdin/product")
    public ResponseEntity<Object> product(@Valid AladdinProductRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(service.product(request));
    }
}

