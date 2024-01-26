package com.bookstore.readme.domain.collection.aladin.controller;

import com.bookstore.readme.domain.collection.aladin.request.list.AladdinListRequest;
import com.bookstore.readme.domain.collection.aladin.service.CollectionService;
import com.bookstore.readme.domain.collection.aladin.request.product.AladdinProductRequest;
import com.bookstore.readme.domain.collection.aladin.request.search.AladdinSearchRequest;
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

@RequestMapping("/collection/aladdin")
public class CollectionController {
    private final CollectionService service;

    @GetMapping("/search")
    public ResponseEntity<Object> search(@Valid AladdinSearchRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(service.search(request));
    }

    @GetMapping("/list")
    public ResponseEntity<Object> list(@Valid AladdinListRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(service.list(request));
    }

    @GetMapping("/product")
    public ResponseEntity<Object> product(@Valid AladdinProductRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(service.product(request));
    }
}

