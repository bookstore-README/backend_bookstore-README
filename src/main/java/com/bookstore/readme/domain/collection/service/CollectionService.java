package com.bookstore.readme.domain.collection.service;

import com.bookstore.readme.domain.collection.dto.aladdin.AladinBookDto;
import com.bookstore.readme.domain.collection.request.list.AladdinListRequest;
import com.bookstore.readme.domain.collection.request.search.AladdinSearchRequest;
import com.bookstore.readme.domain.collection.response.AladdinResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface CollectionService {
    AladinBookDto search(AladdinSearchRequest request) throws JsonProcessingException;

    AladinBookDto list(AladdinListRequest request) throws JsonProcessingException;
}