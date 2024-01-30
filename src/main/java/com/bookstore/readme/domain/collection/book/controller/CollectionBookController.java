package com.bookstore.readme.domain.collection.book.controller;

import com.bookstore.readme.domain.collection.book.request.SaveDto;
import com.bookstore.readme.domain.collection.book.service.CollectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/collection/book")
public class CollectionBookController {
    private final CollectionService collectionService;

    @PostMapping("/search/save")
    public ResponseEntity<Object> saveSearch(@Validated @RequestBody SaveDto request) {
        return ResponseEntity.ok(collectionService.save(request));
    }
}
