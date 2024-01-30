package com.bookstore.readme.domain.collection.book.service;

import com.bookstore.readme.domain.collection.aladin.dto.BookDto;
import com.bookstore.readme.domain.collection.book.request.SaveDto;

import java.util.List;

public abstract class CollectionService {
    public abstract List<BookDto> save(SaveDto saveDto);
}
