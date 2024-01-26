package com.bookstore.readme.domain.collection.aladin.service;

import com.bookstore.readme.domain.collection.aladin.request.list.AladdinListRequest;
import com.bookstore.readme.domain.collection.aladin.dto.BookDto;
import com.bookstore.readme.domain.collection.aladin.request.product.AladdinProductRequest;
import com.bookstore.readme.domain.collection.aladin.request.search.AladdinSearchRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface CollectionService {
    BookDto search(AladdinSearchRequest request) throws JsonProcessingException;

    BookDto list(AladdinListRequest request) throws JsonProcessingException;

    BookDto product(AladdinProductRequest request) throws JsonProcessingException;
}
