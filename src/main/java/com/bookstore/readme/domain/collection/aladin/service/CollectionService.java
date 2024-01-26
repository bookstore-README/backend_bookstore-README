package com.bookstore.readme.domain.collection.service;

import com.bookstore.readme.domain.collection.dto.aladdin.BookDto;
import com.bookstore.readme.domain.collection.request.list.AladdinListRequest;
import com.bookstore.readme.domain.collection.request.product.AladdinProductRequest;
import com.bookstore.readme.domain.collection.request.search.AladdinSearchRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface CollectionService {
    BookDto search(AladdinSearchRequest request) throws JsonProcessingException;

    BookDto list(AladdinListRequest request) throws JsonProcessingException;

    BookDto product(AladdinProductRequest request) throws JsonProcessingException;
}
