package com.bookstore.readme.domain.collection.aladin.service;

import com.bookstore.readme.domain.collection.aladin.request.list.AladdinListRequest;
import com.bookstore.readme.domain.collection.aladin.dto.BookDto;
import com.bookstore.readme.domain.collection.aladin.request.product.AladdinProductRequest;
import com.bookstore.readme.domain.collection.aladin.request.search.AladdinSearchRequest;
import com.bookstore.readme.domain.collection.aladin.service.search.AladdinListService;
import com.bookstore.readme.domain.collection.aladin.service.search.AladdinProductService;
import com.bookstore.readme.domain.collection.aladin.service.search.AladdinSearchService;
import com.bookstore.readme.domain.collection.aladin.service.search.AladdinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
public class AladdinCollectionService implements CollectionService {
    private final AladdinService<AladdinSearchRequest> aladdinSearchService;
    private final AladdinService<AladdinListRequest> aladdinListService;
    private final AladdinService<AladdinProductRequest> aladdinProductService;

    public AladdinCollectionService(AladdinSearchService aladdinSearchService, AladdinListService aladdinListService, AladdinProductService aladdinProductService) {
        this.aladdinSearchService = aladdinSearchService;
        this.aladdinListService = aladdinListService;
        this.aladdinProductService = aladdinProductService;
    }

    @Override
    public BookDto search(AladdinSearchRequest request) {
        return aladdinSearchService.search(request);
    }

    @Override
    public BookDto list(AladdinListRequest request) {
        return aladdinListService.search(request);
    }

    @Override
    public BookDto product(AladdinProductRequest request) {
        return aladdinProductService.search(request);
    }
}
